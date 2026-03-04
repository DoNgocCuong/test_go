package com.example.test_golden_owl.config;

import com.example.test_golden_owl.Repository.*;
import com.example.test_golden_owl.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MonThiRepository monThiRepository;
    private final DiemThiRepository diemThiRepository;
    private final NgoaiNguRepository ngoaiNguRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final String CSV_URL =
            "https://raw.githubusercontent.com/GoldenOwlAsia/webdev-intern-assignment-3/main/dataset/diem_thi_thpt_2024.csv";

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (diemThiRepository.count() > 0) return;

        System.out.println(">>> START IMPORT <<<");

        int maxRows = 10000;
        int batchSize = 1000;
        int count = 0;

        // ==============================
        // 1. Subject mapping
        // ==============================

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

        // Tạo môn nếu chưa có
        subjectMap.values().forEach(tenMon -> {
            if (!monThiRepository.existsByTenMon(tenMon)) {
                monThiRepository.save(
                        MonThi.builder()
                                .tenMon(tenMon)
                                .build()
                );
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

        // ==============================
        // 2. Read CSV
        // ==============================

        URL url = new URL(CSV_URL);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(url.openStream()));

        String headerLine = reader.readLine();
        String[] headers = headerLine.split(",");

        String line;

        while ((line = reader.readLine()) != null) {

            if (count >= maxRows) break;

            String[] columns = line.split(",", -1);
            String sbd = columns[0];

            // Ngoại ngữ
            String maNgoaiNgu = columns[headers.length - 1];
            NgoaiNgu ngoaiNgu = null;

            if (!maNgoaiNgu.isBlank()) {
                ngoaiNgu = ngoaiNguMap.get(maNgoaiNgu);

                if (ngoaiNgu == null) {
                    ngoaiNgu = new NgoaiNgu();
                    ngoaiNgu.setMaNgoaiNgu(maNgoaiNgu);
                    ngoaiNgu = ngoaiNguRepository.save(ngoaiNgu);
                    ngoaiNguMap.put(maNgoaiNgu, ngoaiNgu);
                }
            }

            // Điểm từng môn
            for (int i = 1; i < headers.length - 1; i++) {

                if (!columns[i].isBlank()) {

                    Double diem = Double.parseDouble(columns[i]);

                    String tenMon = subjectMap.get(headers[i]);
                    MonThi monThi = monThiMap.get(tenMon);

                    DiemThi diemThi = new DiemThi();
                    diemThi.setSbd(sbd);
                    diemThi.setMonThi(monThi);
                    diemThi.setDiem(diem);

                    if (headers[i].equals("ngoai_ngu")) {
                        diemThi.setNgoaiNgu(ngoaiNgu);
                    }

                    diemThiRepository.save(diemThi);
                }
            }

            count++;

            if (count % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
                System.out.println("Inserted: " + count);
            }
        }

        entityManager.flush();
        entityManager.clear();
        reader.close();

        System.out.println(">>> IMPORT DONE: " + count + " records <<<");
    }
}