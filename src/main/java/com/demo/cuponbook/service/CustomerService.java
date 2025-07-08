package com.demo.cuponbook.service;

import com.demo.cuponbook.dto.OrderDTO;
import com.demo.cuponbook.dto.StampSaveDTO;
import com.demo.cuponbook.entity.*;
import com.demo.cuponbook.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final StampLogRepository stampLogRepository;
    private final StampLogInfRepository stampLogInfRepository;
    private final StampLogHisRepository stampLogHisRepository;
    private final  CouponRepository couponRepository;

    @Transactional
    public void saveStamp(StampSaveDTO saveDto, OrderDTO orderDto) {
        String phone = saveDto.getCustomerPhone();
        int amount = orderDto.getTotalPrice();
        int iceCnt = orderDto.getIceQty();
        int hotCnt =  orderDto.getHotQty();

        Optional<Customer> optionalCustomer = customerRepository.findByCustomerPhone(phone);
        Customer customer;

        //회원이 존재하지 않는 경우
        if (!optionalCustomer.isPresent()) {
            customer = Customer.builder()
                    .customerPhone(phone)
                    .stampCnt(0)
                    .couponCnt(0)
                    .usedCouponCnt(0)
                    .crtTm(LocalDateTime.now())
                    .chgTm(LocalDateTime.now())
                    .build();

            customerRepository.save(customer);

            optionalCustomer = customerRepository.findByCustomerPhone(phone);
        }

        //회원이 존재하는경우
        customer = optionalCustomer.get();

        // 적립될 스탬프 수
        int saveStampCnt = (iceCnt + hotCnt) >= 10 ? (iceCnt+hotCnt)/10 : (iceCnt+hotCnt);
        int saveCouponCnt = (iceCnt + hotCnt) >= 10 ? (iceCnt+hotCnt)%10 : 0;
        int totalStamp = customer.getStampCnt() + saveStampCnt;

        //StampLog/Inf/His DB Write
        SaveStampLog(customer,amount,saveStampCnt);

        //CouponLogSave And customer Setting
        customer = SaveCouponLog(customer, totalStamp);

        customerRepository.save(customer);
    }

    private Customer SaveCouponLog(Customer customer, int totalStamp) {
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

        return customer;
    }

    private void SaveStampLog(Customer customer, int amount, int saveStampCnt) {
        // 스탬프 로그 저장
        StampLog log = StampLog.builder()
                .customer(customer)
                .paymentAmount(amount)
                .stampCnt(saveStampCnt)
                .regDate(LocalDateTime.now())
                .build();
        stampLogRepository.save(log);

        //INF Table 저장
        //Customer 정보가 존재하는지 확인
        Optional<StampLogInf> optionalStampLogInf = stampLogInfRepository.findByCustomer(customer);

        //존재할 경우 업데이트
        if (optionalStampLogInf.isPresent()) {
            StampLogInf infLog = optionalStampLogInf.get();

            infLog.setStampCnt(infLog.getStampCnt() + saveStampCnt);
            infLog.setRegDate(LocalDateTime.now());

            stampLogInfRepository.save(infLog);
        }
        else //존재하지 않을경우 생성
        {
            StampLogInf infLog = StampLogInf.builder()
                    .customer(customer)
                    .phoneNumber(customer.getCustomerPhone())
                    .paymentAmount(amount)
                    .stampCnt(saveStampCnt)
                    .regDate(LocalDateTime.now())
                    .build();
            stampLogInfRepository.save(infLog);
        }

        //HIS Table 저장
        StampLogHis stampLogHis = StampLogHis.builder()
                .customer(customer)
                .phoneNumber(customer.getCustomerPhone())
                .paymentAmount(amount)
                .stampCnt(saveStampCnt)
                .regDate(LocalDateTime.now())
                .build();

        stampLogHisRepository.save(stampLogHis);
    }
}
