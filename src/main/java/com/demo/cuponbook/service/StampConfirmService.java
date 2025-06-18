package com.demo.cuponbook.service;


import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.entity.StampLog;
import com.demo.cuponbook.repository.CustomerRepository;
import com.demo.cuponbook.repository.StampLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StampConfirmService {
    private final CustomerRepository customerRepository;
    private final StampLogRepository stampLogRepository;

    public StampConfirmService(CustomerRepository customerRepository, StampLogRepository stampLogRepository) {
        this.customerRepository = customerRepository;
        this.stampLogRepository = stampLogRepository;
    }

    public Customer findCustomerByPhone(String phone) {
        return customerRepository.findByCustomerPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException("해당 전화번호의 고객이 없습니다."));
    }

    public StampLog findByAllCustomer(Customer customer) {
        System.out.println("customer : " + customer.getCustomerId());

        //중복 데이터 전부 가져옴
        List<StampLog> logs = stampLogRepository.findAllByCustomer(customer);

        if (logs.isEmpty()) {
            System.out.println("❌ StampLog 데이터가 존재하지 않음!");
            throw new IllegalArgumentException("해당 고객의 StampLog가 없습니다.");
        }

        //log중 최근 데이터 하나만 가져오기
        return logs.getFirst();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void useCoupon(String phone) {
        Customer customer = findCustomerByPhone(phone);

        if (customer.getCouponCnt() < 1) {
            throw new IllegalStateException("사용 가능한 쿠폰이 없습니다.");
        }

        //쿠폰 차감
        customer.setCouponCnt(customer.getCouponCnt() - 1);
        //DB 저장
        saveCustomer(customer);
    }
}
