package com.example.test_golden_owl.dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CheckScoreResponse {
    String sbd;
    Double toan;
    Double nguVan;
    Double ngoaiNgu;
    Double vatLi;
    Double hoaHoc;
    Double sinhHoc;
    Double lichSu;
    Double diaLi;
    Double gdcd;
    String maNgoaiNgu;
}
