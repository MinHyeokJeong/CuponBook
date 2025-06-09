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
}
