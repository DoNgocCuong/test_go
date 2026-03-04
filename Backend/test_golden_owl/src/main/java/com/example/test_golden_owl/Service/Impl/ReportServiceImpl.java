package com.example.test_golden_owl.Service.Impl;

import com.example.test_golden_owl.Repository.DiemThiRepository;
import com.example.test_golden_owl.Service.ReportService;
import com.example.test_golden_owl.dto.Response.ReportResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportServiceImpl implements ReportService {
    DiemThiRepository diemThiRepository;
    @Override
    public ReportResponse report(String tenMon) {
        return diemThiRepository.reportBySubject(tenMon);
    }
}
