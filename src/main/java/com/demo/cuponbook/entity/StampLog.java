package com.demo.cuponbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "STAMP_LOG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StampLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAMP_ID")
    private Long stampId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @Column(name = "PAYMENT_AMOUNT")
    private Integer paymentAmount;

    @Column(name = "STAMP_CNT", nullable = false)
    private Integer stampCnt;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;
}
