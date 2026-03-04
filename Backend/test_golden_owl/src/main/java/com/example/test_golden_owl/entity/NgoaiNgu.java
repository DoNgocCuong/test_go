package com.example.test_golden_owl.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ngoai_ngu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NgoaiNgu {
    @Id
    @Column(name = "ma_ngoai_ngu")
    private String maNgoaiNgu;


    @OneToMany(mappedBy = "ngoaiNgu", fetch = FetchType.LAZY)
    private List<DiemThi> diemThis;
}
