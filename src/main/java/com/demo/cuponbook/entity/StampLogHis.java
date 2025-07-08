package com.demo.cuponbook.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
//@Data -> @Getter/@Setter : 변경 사유는 Data를 쓸 경우 toString(), equals(), hashCode()까지 함께 자동 생성
//hashCode 같은 메서드 호출 시 서로 참조하며 OverFlow 발생
@Entity
@Table(name = "STAMP_LOG_HIS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StampLogHis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAMP_ID")
    private Long stampId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @Column(name = "PHONE_NUM")
    private String phoneNumber;

    @Column(name = "PAYMENT_AMOUNT")
    private Integer paymentAmount;

    @Column(name = "STAMP_CNT", nullable = false)
    private Integer stampCnt;

    @Column(name = "REG_DATE")
    private LocalDateTime regDate;
}
