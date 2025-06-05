package com.demo.cuponbook.repository;

import com.demo.cuponbook.entity.StampLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampLogRepository extends JpaRepository<StampLog, Long> {
}
