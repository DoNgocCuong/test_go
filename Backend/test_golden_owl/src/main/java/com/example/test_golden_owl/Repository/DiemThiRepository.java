package com.example.test_golden_owl.Repository;

import com.example.test_golden_owl.dto.Response.ReportResponse;
import com.example.test_golden_owl.dto.Response.TopResponse;
import com.example.test_golden_owl.entity.DiemThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DiemThiRepository extends JpaRepository<DiemThi,Long> {

    // ✅ Tối ưu checkScore (giữ nguyên)
    @Query("""
           SELECT d
           FROM DiemThi d
           JOIN FETCH d.monThi
           LEFT JOIN FETCH d.ngoaiNgu
           WHERE d.sbd = :sbd
           """)
    List<DiemThi> findAllBySbd(@Param("sbd") String sbd);


    // ✅ FIX lỗi Long mismatch + tối ưu CASE
    @Query("""
    SELECT new com.example.test_golden_owl.dto.Response.ReportResponse(
        m.tenMon,
        SUM(CASE WHEN d.diem >= 8 THEN 1L ELSE 0L END),
        SUM(CASE WHEN d.diem >= 6 AND d.diem < 8 THEN 1L ELSE 0L END),
        SUM(CASE WHEN d.diem >= 4 AND d.diem < 6 THEN 1L ELSE 0L END),
        SUM(CASE WHEN d.diem < 4 THEN 1L ELSE 0L END)
    )
    FROM DiemThi d
    JOIN d.monThi m
    WHERE m.tenMon = :tenMon
    GROUP BY m.tenMon
    """)
    ReportResponse reportBySubject(@Param("tenMon") String tenMon);


    @Query("""
    SELECT new com.example.test_golden_owl.dto.Response.TopResponse(
        d.sbd,
        SUM(CASE WHEN d.monThi.id = :toanId THEN d.diem ELSE 0.0 END),
        SUM(CASE WHEN d.monThi.id = :lyId THEN d.diem ELSE 0.0 END),
        SUM(CASE WHEN d.monThi.id = :hoaId THEN d.diem ELSE 0.0 END),
        SUM(d.diem)
    )
    FROM DiemThi d
    WHERE d.monThi.id IN (:toanId, :lyId, :hoaId)
    GROUP BY d.sbd
    ORDER BY SUM(d.diem) DESC
    """)
    List<TopResponse> topKhoiA(
            @Param("toanId") Long toanId,
            @Param("lyId") Long lyId,
            @Param("hoaId") Long hoaId,
            Pageable pageable
    );

}