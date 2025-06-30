package com.demo.cuponbook.controller;

import com.demo.cuponbook.dto.OrderDTO;
import com.demo.cuponbook.dto.StampSaveDTO;
import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.service.CustomerService;
import com.demo.cuponbook.service.StampConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    CustomerService customerService;
    @Autowired
    StampConfirmService stampConfirmService;

    @GetMapping("/")
    public String indexPage() {
        return "stamp";
    }
    @GetMapping("/order")
    public String orderPage() {
        return "order";
    }

    @PostMapping("/order")
    public String confirmOrder(@ModelAttribute OrderDTO orderDTO, Model model) {
        System.out.println(orderDTO);
        System.out.printf("총합계: " + orderDTO.getTotalPrice());

        model.addAttribute("msg", "주문이 완료되었습니다!");
        return "stamp";
    }

    @PostMapping("/stamp")
    public String index(@RequestBody StampSaveDTO stampSaveDTO, Model model) {
        try {
            customerService.collectStamp(stampSaveDTO);
            Customer customer = stampConfirmService.findCustomerByPhone(stampSaveDTO.getCustomerPhone());

            int stampCount = customer.getStampCnt();
            int totalStamp = 10;
            int useableCoupon = customer.getCouponCnt();

            model.addAttribute("stampCount", stampCount);
            model.addAttribute("totalStamp", totalStamp);
            model.addAttribute("useableCoupon", useableCoupon);
            model.addAttribute("phone", stampSaveDTO.getCustomerPhone());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/showStamp?phone=" + stampSaveDTO.getCustomerPhone();
    }

    @GetMapping("/stamp")
    public String saveCoupon(@ModelAttribute OrderDTO orderDTO, Model model) {
        model.addAttribute("iceQty", orderDTO.getIceQty());
        model.addAttribute("hotQty", orderDTO.getHotQty());
        model.addAttribute("totalPrice", orderDTO.getTotalPrice());
        return "stamp";
    }

}
