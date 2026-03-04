package com.example.test_golden_owl.Controller;

import com.example.test_golden_owl.Service.ReportService;
import com.example.test_golden_owl.dto.Response.ApiResponse;
import com.example.test_golden_owl.dto.Response.ReportResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/report")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportController {
    ReportService reportService;
    @GetMapping("/{tenMon}")
    public ApiResponse<ReportResponse> report(@PathVariable String tenMon){
        return ApiResponse.<ReportResponse>builder()
                .message("successfully")
                .result(reportService.report(tenMon))
                .build();
    }
}
