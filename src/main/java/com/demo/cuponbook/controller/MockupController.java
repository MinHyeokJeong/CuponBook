package com.demo.cuponbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MockupController {
    @GetMapping("/mockup/order")
    public String mkOrder() {
        return "/mockup/order";
    }

    @GetMapping("/mockup/coupon")
    public String mockupCoupon() {
        return "/mockup/coupon";
    }
    @GetMapping("/mockup/payment")
    public String mockupPayment() {
        return "/mockup/payment";
    }
    @GetMapping("/mockup/stamp")
    public String mockupStamp() {
        return "/mockup/stamp";
    }
    @GetMapping("/mockup/stampinfo")
    public String mockupStampInfo() {
        return "/mockup/stampinfo";
    }

}
