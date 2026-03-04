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
    List<DiemThi> findAllBySbd(String sbd);
    @Query("""
    SELECT new com.example.test_golden_owl.dto.Response.ReportResponse(
        m.tenMon,
        COALESCE(SUM(CASE WHEN d.diem >= 8 THEN 1 ELSE 0 END),0),
        COALESCE(SUM(CASE WHEN d.diem < 8 AND d.diem >= 6 THEN 1 ELSE 0 END),0),
        COALESCE(SUM(CASE WHEN d.diem < 6 AND d.diem >= 4 THEN 1 ELSE 0 END),0),
        COALESCE(SUM(CASE WHEN d.diem < 4 THEN 1 ELSE 0 END),0)
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
        SUM(CASE WHEN m.tenMon = 'Toán' THEN d.diem ELSE 0 END),
        SUM(CASE WHEN m.tenMon = 'Vật Lý' THEN d.diem ELSE 0 END),
        SUM(CASE WHEN m.tenMon = 'Hóa Học' THEN d.diem ELSE 0 END),
        SUM(d.diem)
    )
    FROM DiemThi d
    JOIN d.monThi m
    WHERE m.tenMon IN ('Toán','Vật Lý','Hóa Học')
    GROUP BY d.sbd
    ORDER BY SUM(d.diem) DESC
    """)
    List<TopResponse> topKhoiA(Pageable pageable);
}
