package com.example.test_golden_owl.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "diem_thi",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"sbd", "mon_thi_id"}
        )
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiemThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sbd", nullable = false)
    private String sbd;

    @Column(name = "diem", nullable = false)
    private Double diem;

    // ✅ FK đúng theo migration mới
    @ManyToOne
    @JoinColumn(name = "mon_thi_id", nullable = false)
    private MonThi monThi;

    @ManyToOne
    @JoinColumn(name = "ma_ngoai_ngu")
    private NgoaiNgu ngoaiNgu;
}