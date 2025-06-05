package com.demo.cuponbook.service;

import com.demo.cuponbook.dto.StampSaveDTO;
import com.demo.cuponbook.entity.Coupon;
import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.entity.StampLog;
import com.demo.cuponbook.repository.CouponRepository;
import com.demo.cuponbook.repository.CustomerRepository;
import com.demo.cuponbook.repository.StampLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final StampLogRepository stampLogRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public void collectStamp(StampSaveDTO dto) {
        String phone = dto.getCustomerPhone();
        int amount = dto.getPaymentAmount();

        //
        Customer customer = customerRepository.findByCustomerPhone(phone)
                .orElseGet(() -> {
                    Customer newCustomer = Customer.builder()
                            .customerPhone(phone)
                            .stampCnt(0)
                            .couponCnt(0)
                            .usedCouponCnt(0)
                            .crtTm(LocalDateTime.now())
                            .chgTm(LocalDateTime.now())
                            .build();
                    return customerRepository.save(newCustomer);
                });

        // 적립될 스탬프 수 계산 (예: 만원 당 1개)
        int addStamp = amount / 10000;
        int totalStamp = customer.getStampCnt() + addStamp;

        // 스탬프 로그 저장
        StampLog log = StampLog.builder()
                .customer(customer)
                .paymentAmount(amount)
                .stampCnt(addStamp)
                .regDate(LocalDateTime.now())
                .build();
        stampLogRepository.save(log);

        // 쿠폰 처리
        if (totalStamp >= 10) {
            int newCouponCnt = totalStamp / 10;
            int remainingStamps = totalStamp % 10;

            customer.setCouponCnt(customer.getCouponCnt() + newCouponCnt);
            customer.setStampCnt(remainingStamps);
            customer.setChgTm(LocalDateTime.now());

            for (int i = 0; i < newCouponCnt; i++) {
                Coupon coupon = Coupon.builder()
                        .customer(customer)
                        .createCouponDate(LocalDateTime.now())
                        .isUsed(false)
                        .build();
                couponRepository.save(coupon);
            }

        } else {
            customer.setStampCnt(totalStamp);
            customer.setChgTm(LocalDateTime.now());
        }
        customerRepository.save(customer);
    }
}
