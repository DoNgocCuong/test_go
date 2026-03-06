package com.example.test_golden_owl.config;

import com.example.test_golden_owl.Repository.MonThiRepository;
import com.example.test_golden_owl.Repository.NgoaiNguRepository;
import com.example.test_golden_owl.entity.MonThi;
import com.example.test_golden_owl.entity.NgoaiNgu;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataInitializer implements CommandLineRunner {

    private final MonThiRepository monThiRepository;
    private final NgoaiNguRepository ngoaiNguRepository;
    private final JdbcTemplate jdbcTemplate;

    private static final String CSV_URL =
            "https://raw.githubusercontent.com/GoldenOwlAsia/webdev-intern-assignment-3/main/dataset/diem_thi_thpt_2024.csv";

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        System.out.println("Database: " +
                jdbcTemplate.queryForObject("SELECT DATABASE()", String.class));

        // Kiểm tra nếu đã có dữ liệu thì bỏ qua
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM diem_thi",
                Long.class
        );

        if (count != null && count > 0) {
            System.out.println("Data already exists (" + count + " rows). Skip import.");
            return;
        }

        System.out.println(">>> START FAST IMPORT <<<");

        Map<String, String> subjectMap = new HashMap<>();
        subjectMap.put("toan", "Toán");
        subjectMap.put("ngu_van", "Ngữ Văn");
        subjectMap.put("ngoai_ngu", "Ngoại Ngữ");
        subjectMap.put("vat_li", "Vật Lý");
        subjectMap.put("hoa_hoc", "Hóa Học");
        subjectMap.put("sinh_hoc", "Sinh Học");
        subjectMap.put("lich_su", "Lịch Sử");
        subjectMap.put("dia_li", "Địa Lý");
        subjectMap.put("gdcd", "GDCD");

        // Tạo môn thi nếu chưa có
        subjectMap.values().forEach(tenMon -> {
            if (!monThiRepository.existsByTenMon(tenMon)) {
                monThiRepository.save(MonThi.builder().tenMon(tenMon).build());
            }
        });

        Map<String, MonThi> monThiMap =
                monThiRepository.findAll()
                        .stream()
                        .collect(Collectors.toMap(MonThi::getTenMon, m -> m));

        Map<String, NgoaiNgu> ngoaiNguMap =
                ngoaiNguRepository.findAll()
                        .stream()
                        .collect(Collectors.toMap(NgoaiNgu::getMaNgoaiNgu, n -> n));

        System.out.println(">>> Connecting to CSV: " + CSV_URL);
        URL url = new URL(CSV_URL);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(url.openStream()));

        String[] headers = reader.readLine().split(",");
        System.out.println(">>> CSV Headers: " + Arrays.toString(headers));

        // Kiểm tra headers có khớp subjectMap không
        for (int i = 1; i < headers.length - 1; i++) {
            String h = headers[i].trim();
            if (!subjectMap.containsKey(h)) {
                System.out.println("⚠️ Header không có trong subjectMap: [" + h + "]");
            }
        }

        String line;
        int batchSize = 1000;
        int totalInserted = 0;
        int skippedRows = 0;
        List<Object[]> batchArgs = new ArrayList<>();

        while ((line = reader.readLine()) != null) {

            String[] columns = line.split(",", -1);

            if (columns.length < headers.length) {
                skippedRows++;
                continue;
            }

            String sbd = columns[0].trim();
            String maNgoaiNgu = columns[headers.length - 1].trim();
            String ngoaiNguKey = null;

            if (!maNgoaiNgu.isBlank()) {
                NgoaiNgu nn = ngoaiNguMap.get(maNgoaiNgu);
                if (nn == null) {
                    nn = new NgoaiNgu();
                    nn.setMaNgoaiNgu(maNgoaiNgu);
                    nn = ngoaiNguRepository.saveAndFlush(nn);
                    ngoaiNguMap.put(maNgoaiNgu, nn);
                }
                ngoaiNguKey = nn.getMaNgoaiNgu();
            }

            for (int i = 1; i < headers.length - 1; i++) {

                String colValue = columns[i].trim();
                if (colValue.isBlank()) continue;

                String headerKey = headers[i].trim();
                String tenMon = subjectMap.get(headerKey);

                if (tenMon == null) {
                    System.out.println("⚠️ Không tìm thấy môn cho header: [" + headerKey + "]");
                    continue;
                }

                MonThi monThi = monThiMap.get(tenMon);
                if (monThi == null) {
                    System.out.println("⚠️ Không tìm thấy MonThi trong DB: [" + tenMon + "]");
                    continue;
                }

                try {
                    Double diem = Double.parseDouble(colValue);
                    batchArgs.add(new Object[]{
                            sbd,
                            monThi.getId(),
                            diem,
                            headers[i].trim().equals("ngoai_ngu") ? ngoaiNguKey : null
                    });
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Không parse được điểm: [" + colValue + "] tại sbd=" + sbd);
                }
            }

            if (batchArgs.size() >= batchSize) {
                try {
                    jdbcTemplate.batchUpdate(
                            "INSERT INTO diem_thi (sbd, mon_thi_id, diem, ma_ngoai_ngu) " +
                                    "VALUES (?, ?, ?, ?) " +
                                    "ON DUPLICATE KEY UPDATE diem = VALUES(diem)",
                            batchArgs
                    );
                    totalInserted += batchArgs.size();
                    System.out.println("Inserted rows diem_thi table: " + totalInserted);
                } catch (Exception e) {
                    System.err.println(" BATCH INSERT ERROR: " + e.getMessage());
                    e.printStackTrace();
                }
                batchArgs.clear();
            }
        }

        // Insert phần còn lại
        if (!batchArgs.isEmpty()) {
            try {
                jdbcTemplate.batchUpdate(
                        "INSERT INTO diem_thi (sbd, mon_thi_id, diem, ma_ngoai_ngu) " +
                                "VALUES (?, ?, ?, ?) " +
                                "ON DUPLICATE KEY UPDATE diem = VALUES(diem)",
                        batchArgs
                );
                totalInserted += batchArgs.size();
            } catch (Exception e) {
                System.err.println(" FINAL BATCH INSERT ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }

        reader.close();

        System.out.println(">>> IMPORT DONE. Total processed: " + totalInserted + " <<<");
        if (skippedRows > 0) {
            System.out.println(" Skipped rows (thiếu cột): " + skippedRows);
        }
    }
}