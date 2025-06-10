package com.demo.cuponbook.controller;

import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.service.CustomerService;
import com.demo.cuponbook.service.StampConfirmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

        model.addAttribute("stampCount", stampCount);
        model.addAttribute("totalStamp", totalStamp);

        return "showStamp";  // showStamp.html 뷰 반환
    }


}
