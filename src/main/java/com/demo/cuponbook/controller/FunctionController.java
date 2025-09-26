package com.demo.cuponbook.controller;

import com.demo.cuponbook.dto.CouponUseRequest;
import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.entity.StampLog;
import com.demo.cuponbook.service.StampConfirmService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class FunctionController {
        private final StampConfirmService stampConfirmService;

    public FunctionController(StampConfirmService stampConfirmService) {
        this.stampConfirmService = stampConfirmService;
    }

    @GetMapping("/showStamp")
    public String showStamp(@RequestParam String phone,
                            Model model,
                            @RequestParam(required = false) Integer iceQty,
                            @RequestParam(required = false) Integer hotQty,
                            @RequestParam(required = false) Integer totalPrice) {
        Customer customer = stampConfirmService.findCustomerByPhone(phone);
        System.out.println(phone);
        int totalStamp = 10;
        // 적립 예정인 제품 수량 및 결제 가격
        model.addAttribute("iceQty", iceQty);
        model.addAttribute("hotQty", hotQty);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("phone", phone);
        model.addAttribute("totalStamp", totalStamp);

        if (customer == null) {
            model.addAttribute("error", "존재하지 않는 사용자입니다." );
        } else {
            model.addAttribute("stampCount", customer.getStampCnt());
            model.addAttribute("useableCoupon", customer.getCouponCnt());
        }
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
