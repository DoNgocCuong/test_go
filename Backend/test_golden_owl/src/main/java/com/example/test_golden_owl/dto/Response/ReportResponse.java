package com.example.test_golden_owl.dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReportResponse {
    String tenMon;
    Long levelA;
    Long levelB;
    Long levelC;
    Long levelD;
}
