package com.demo.cuponbook.repository;

import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.entity.StampLogHis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StampLogHisRepository extends JpaRepository<StampLogHis, Long> {
    Optional<StampLogHis> findAllByCustomer(Customer customer);
}
