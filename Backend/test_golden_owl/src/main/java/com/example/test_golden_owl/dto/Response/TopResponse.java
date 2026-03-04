package com.example.test_golden_owl.dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TopResponse {
    String sbd;
    Double toan;
    Double vatLy;
    Double hoaHoc;
    Double tongDiem;
}
