package com.demo.cuponbook.repository;

import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.entity.StampLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StampLogRepository extends JpaRepository<StampLog, Long> {
    List<StampLog> findAllByCustomer(Customer customer);
}
