package com.demo.cuponbook.controller;

import com.demo.cuponbook.dto.CouponUseRequest;
import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.entity.StampLog;
import com.demo.cuponbook.service.CustomerService;
import com.demo.cuponbook.service.StampConfirmService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FunctionController {
        private final StampConfirmService stampConfirmService;

    public FunctionController(StampConfirmService stampConfirmService) {
        this.stampConfirmService = stampConfirmService;
    }

    @GetMapping("/showStamp")
    public String showStamp(@RequestParam String phone, Model model) {
        Customer customer = stampConfirmService.findCustomerByPhone(phone);

        int stampCount = customer.getStampCnt();    // 적립된 스탬프 개수
        int totalStamp = 10;                          // 총 스탬프 개수 (고정 또는 DB에서 가져오기)
        int useableCoupon = customer.getCouponCnt(); // 사용가능한 쿠폰 갯수

        model.addAttribute("stampCount", stampCount);
        model.addAttribute("totalStamp", totalStamp);
        model.addAttribute("useableCoupon", useableCoupon);
        model.addAttribute("phone", phone);

        return "showStamp";  // showStamp.html 뷰 반환
    }

    @PostMapping("/showStamp")
    @ResponseBody
    public ResponseEntity<String> handleCouponUse(@RequestBody CouponUseRequest request) {
        try {
            stampConfirmService.useCoupon(request.getPhone());
            return ResponseEntity.ok("쿠폰 사용 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/payment")
    public String costPayment(@RequestParam String phone, Model model) {
        Customer customer = stampConfirmService.findCustomerByPhone(phone);
        StampLog stampLog = stampConfirmService.findByAllCustomer(customer);

        model.addAttribute("customer", customer);
        model.addAttribute("stampCnt", customer.getStampCnt());
        model.addAttribute("couponCnt", customer.getCouponCnt());
        model.addAttribute("paymentAmount", stampLog.getPaymentAmount());

        return "payment";
    }

}
