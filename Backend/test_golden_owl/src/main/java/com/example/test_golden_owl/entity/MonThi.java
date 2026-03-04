package com.example.test_golden_owl.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "mon_thi")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonThi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_mon", nullable = false, unique = true)
    private String tenMon;

    @OneToMany(mappedBy = "monThi", fetch = FetchType.LAZY)
    private List<DiemThi> diemThis;
}