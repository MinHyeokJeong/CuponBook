package com.demo.cuponbook.controller;

import com.demo.cuponbook.dto.CouponUseRequest;
import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.entity.StampLogInf;
import com.demo.cuponbook.service.StampConfirmService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FunctionController {
    public boolean couponEvent = false;
    private final StampConfirmService stampConfirmService;

    public FunctionController(StampConfirmService stampConfirmService) {
        this.stampConfirmService = stampConfirmService;
    }

    //전역변수로 설정(임시)
    private int lastUsedCouponCnt = 0;

    @GetMapping("/showStamp")
    public String showStamp(@RequestParam String phone,
                            @RequestParam int iceQty,
                            @RequestParam int hotQty,
                            @RequestParam int totalPrice,
                            Model model) {
        Customer customer = stampConfirmService.findCustomerByPhone(phone);

        int stampCount = customer.getStampCnt();    // 적립된 스탬프 개수
        int totalStamp = 10;                          // 총 스탬프 개수 (고정 또는 DB에서 가져오기)
        int useableCoupon = customer.getCouponCnt(); // 사용가능한 쿠폰 갯수

        if (couponEvent) {
            totalPrice -= (1000 * lastUsedCouponCnt); // 예: 쿠폰당 1000원 할인
            if (iceQty >= lastUsedCouponCnt) {
                iceQty -= lastUsedCouponCnt;
            } else {
                hotQty -= (lastUsedCouponCnt - iceQty);
                iceQty = 0;
            }

            couponEvent = false;
            lastUsedCouponCnt = 0;
        }

        model.addAttribute("stampCount", stampCount);
        model.addAttribute("totalStamp", totalStamp);
        model.addAttribute("useableCoupon", useableCoupon);
        model.addAttribute("phone", phone);

        model.addAttribute("iceQty", iceQty);
        model.addAttribute("hotQty", hotQty);
        model.addAttribute("totalPrice", totalPrice);
        return "showStamp";  // showStamp.html 뷰 반환
    }

    @PostMapping("/showStamp")
    @ResponseBody
    public ResponseEntity<String> handleCouponUse(@RequestBody CouponUseRequest request) {
        try {
            stampConfirmService.useCoupon(request.getPhone(), request.getUsedCouponCnt());
            couponEvent = true;

            lastUsedCouponCnt = request.getUsedCouponCnt();

            return ResponseEntity.ok("쿠폰 사용 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/payment")
    public String costPayment(@RequestParam String phone, Model model) {
        System.out.println("couponEvent = " + couponEvent);
        Customer customer = stampConfirmService.findCustomerByPhone(phone);
        //StampLog stampLog = stampConfirmService.findByAllCustomer(customer);
        StampLogInf stampLogInf = stampConfirmService.findByCustomerStamp(customer);

        int orderCnt = couponEvent ? stampLogInf.getStampCnt() - 1 : stampLogInf.getStampCnt();
        int paymentAmount = couponEvent ? stampLogInf.getPaymentAmount() - 1000 : stampLogInf.getPaymentAmount();

        model.addAttribute("customer", customer);
        model.addAttribute("stampCnt", customer.getStampCnt());//Custmoer에서 해도되고 stamp로 넘겨도되고
        model.addAttribute("couponCnt", customer.getCouponCnt());
        model.addAttribute("orderCnt", orderCnt);
        model.addAttribute("paymentAmount", paymentAmount);

        //couponEvent = false;

        return "payment";
    }

}
