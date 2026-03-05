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

        System.out.println("Hostname: " +
                jdbcTemplate.queryForObject("SELECT @@hostname", String.class));


        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM diem_thi", Long.class);
        if (count != null && count > 0) {
            System.out.println("Data already exists. Skip import.");
            return;
        }

        System.out.println(">>> START FAST IMPORT <<<");

        Map<String, String> subjectMap = Map.of(
                "toan", "Toán",
                "ngu_van", "Ngữ Văn",
                "ngoai_ngu", "Ngoại Ngữ",
                "vat_li", "Vật Lý",
                "hoa_hoc", "Hóa Học",
                "sinh_hoc", "Sinh Học",
                "lich_su", "Lịch Sử",
                "dia_li", "Địa Lý",
                "gdcd", "GDCD"
        );

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

        URL url = new URL(CSV_URL);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(url.openStream()));

        String[] headers = reader.readLine().split(",");
        String line;

        int batchSize = 1000;
        int totalInserted = 0;
        List<Object[]> batchArgs = new ArrayList<>();

        while ((line = reader.readLine()) != null) {

            String[] columns = line.split(",", -1);
            String sbd = columns[0];

            String maNgoaiNgu = columns[headers.length - 1];
            String ngoaiNguKey = null;

            if (!maNgoaiNgu.isBlank()) {

                NgoaiNgu nn = ngoaiNguMap.get(maNgoaiNgu);

                if (nn == null) {
                    nn = new NgoaiNgu();
                    nn.setMaNgoaiNgu(maNgoaiNgu);
                    nn = ngoaiNguRepository.save(nn);
                    ngoaiNguMap.put(maNgoaiNgu, nn);
                }

                ngoaiNguKey = nn.getMaNgoaiNgu(); // String PK
            }

            for (int i = 1; i < headers.length - 1; i++) {

                if (!columns[i].isBlank()) {

                    Double diem = Double.parseDouble(columns[i]);
                    String tenMon = subjectMap.get(headers[i]);
                    MonThi monThi = monThiMap.get(tenMon);

                    batchArgs.add(new Object[]{
                            sbd,
                            monThi.getId(),
                            diem,
                            headers[i].equals("ngoai_ngu") ? ngoaiNguKey : null
                    });
                }
            }

            if (batchArgs.size() >= batchSize) {

                jdbcTemplate.batchUpdate(
                        "INSERT INTO diem_thi (sbd, mon_thi_id, diem, ma_ngoai_ngu) VALUES (?, ?, ?, ?)",
                        batchArgs
                );

                totalInserted += batchArgs.size();
                batchArgs.clear();

                System.out.println("Inserted rows: " + totalInserted);
            }
        }

        if (!batchArgs.isEmpty()) {
            jdbcTemplate.batchUpdate(
                    "INSERT INTO diem_thi (sbd, mon_thi_id, diem, ma_ngoai_ngu) VALUES (?, ?, ?, ?)",
                    batchArgs
            );
            totalInserted += batchArgs.size();
        }

        reader.close();

        System.out.println(">>> IMPORT DONE. Total rows: " + totalInserted + " <<<");
    }
}