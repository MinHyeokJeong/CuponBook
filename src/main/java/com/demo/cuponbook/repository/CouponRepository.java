package com.demo.cuponbook.repository;

import com.demo.cuponbook.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
