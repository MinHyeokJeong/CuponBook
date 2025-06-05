package com.demo.cuponbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "COUPON")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COUPON_ID")
    private Long couponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @Column(name = "IS_USED", nullable = false)
    private Boolean isUsed;

    @Column(name = "CREATE_COUPON_DATE",nullable = false)
    private LocalDateTime createCouponDate;

    @Column(name = "USED_COUPON_DATE")
    private LocalDateTime usedCouponDate;

    @Column(name = "EXPIRED_DATE")
    private LocalDateTime expiredDate;
}
