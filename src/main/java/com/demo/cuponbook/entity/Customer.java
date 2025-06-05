package com.demo.cuponbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Integer customerId;

    @Column(name = "CUSTOMER_PHONE",nullable = false, unique = true, length = 20)
    private String customerPhone;

    @Column(name = "STAMP_CNT",nullable = false)
    private Integer stampCnt;

    @Column(name = "COUPON_CNT",nullable = false)
    private Integer couponCnt;

    @Column(name = "USED_COUPON_CNT",nullable = false)
    private Integer usedCouponCnt;

    @Column(name = "CRT_TM",nullable = false)
    private LocalDateTime crtTm;

    @Column(name = "CHG_TM",nullable = false)
    private LocalDateTime chgTm;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Coupon> coupons;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StampLog> stampLogs;


}
