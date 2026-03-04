package com.example.test_golden_owl.Repository;

import com.example.test_golden_owl.entity.MonThi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonThiRepository extends JpaRepository<MonThi,Long> {
    boolean existsByTenMon(String maMon);

    Optional<MonThi> findByTenMon(String maMon);
}
