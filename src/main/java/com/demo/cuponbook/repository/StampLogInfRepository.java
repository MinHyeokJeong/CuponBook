package com.demo.cuponbook.repository;

import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.entity.StampLogInf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StampLogInfRepository extends JpaRepository<StampLogInf, Long> {
    Optional<StampLogInf> findByCustomer(Customer customer);
}
