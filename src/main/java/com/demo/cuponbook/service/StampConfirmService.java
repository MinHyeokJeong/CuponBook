package com.demo.cuponbook.service;


import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class StampConfirmService {
    private final CustomerRepository customerRepository;

    public StampConfirmService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomerByPhone(String phone) {
        return customerRepository.findByCustomerPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException("해당 전화번호의 고객이 없습니다."));
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
