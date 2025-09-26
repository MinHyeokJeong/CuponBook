package com.demo.cuponbook.controller;

import com.demo.cuponbook.dto.OrderDTO;
import com.demo.cuponbook.dto.OrderStampDTO;
import com.demo.cuponbook.dto.StampSaveDTO;
import com.demo.cuponbook.dto.UseCouponDTO;
import com.demo.cuponbook.entity.Customer;
import com.demo.cuponbook.entity.StampLog;
import com.demo.cuponbook.service.CustomerService;
import com.demo.cuponbook.service.StampConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
    @Autowired
    CustomerService customerService;
    @Autowired
    StampConfirmService stampConfirmService;

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
    public String index(@ModelAttribute StampSaveDTO stampSaveDTO,
                        @ModelAttribute OrderDTO orderDTO, Model model) {
        try {
            // 결제 확정 된 뒤에 적립 하기 , 처음 적립 시 pending으로
            //customerService.collectStamp(stampSaveDTO);
            customerService.saveStamp(stampSaveDTO, orderDTO);
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
        return "redirect:/showStamp";
    }

    @GetMapping("/stamp")
    public String saveCoupon(@ModelAttribute OrderDTO orderDTO, Model model) {
        model.addAttribute("iceQty", orderDTO.getIceQty());
        model.addAttribute("hotQty", orderDTO.getHotQty());
        model.addAttribute("totalPrice", orderDTO.getTotalPrice());
        return "stamp";
    }


    @PostMapping("/pending")
    public String pendingStamp(@ModelAttribute OrderStampDTO orderStampDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            redirectAttributes.addAttribute("phone", orderStampDTO.getCustomerPhone());
            redirectAttributes.addAttribute("iceQty", orderStampDTO.getIceQty());
            redirectAttributes.addAttribute("hotQty", orderStampDTO.getHotQty());
            redirectAttributes.addAttribute("totalPrice", orderStampDTO.getTotalPrice());

        }catch (Exception e) {
            e.printStackTrace();
            return "redirect:/stamp";
        }
        return "redirect:/showStamp";
    }

    @PostMapping("useCoupon")
    public String useCoupon(@ModelAttribute UseCouponDTO useCouponDTO,
                            RedirectAttributes redirectAttributes) {
        stampConfirmService.useCoupon(useCouponDTO.getPhone());

        // 쿠폰 차감 후 주문 상품 가격 차감하기
        // 현재는 쿠폰 사용시 쿠폰 갯수 1개 차감
        int iceQty = useCouponDTO.getOrderIceQty();
        int hotQty = useCouponDTO.getOrderHotQty();
        int totalPrice = useCouponDTO.getOrderTotalPrice();
        int discountPrice = 0;
        if (iceQty > 0) {
            discountPrice =1500;
            totalPrice -= discountPrice;
        } else if (iceQty == 0 || hotQty > 0) {
            discountPrice =1000;
            totalPrice -= discountPrice;
        }

        // 쿠폰 차감된 항목은 적립 제외
        redirectAttributes.addAttribute("discountPrice", discountPrice);
        redirectAttributes.addAttribute("useCouponCnt", 1);
        redirectAttributes.addAttribute("iceQty", iceQty);
        redirectAttributes.addAttribute("hotQty", hotQty);
        redirectAttributes.addAttribute("totalPrice", totalPrice);
        redirectAttributes.addAttribute("phone", useCouponDTO.getPhone());
        return "useCouponPayment";

    }

    @GetMapping("/useCouponPayment")
    public String costPayment(@RequestParam String phone,  Model model,
                              @RequestParam(required = false) Integer iceQty,
                              @RequestParam(required = false) Integer hotQty,
                              @RequestParam(required = false) Integer totalPrice,
                              @RequestParam(required = false) Integer useCouponCnt,
                              @RequestParam(required = false) Integer discountPrice) {

        try {
            Customer customer = stampConfirmService.findCustomerByPhone(phone);

            model.addAttribute("discountPrice", discountPrice);
            model.addAttribute("useCouponCnt", useCouponCnt);
            model.addAttribute("iceQty", iceQty);
            model.addAttribute("hotQty", hotQty);
            model.addAttribute("totalPrice", totalPrice);

            if (customer == null) {
                Customer newCustomer = new Customer();
                model.addAttribute("customer", newCustomer);
            }else{
                model.addAttribute("customer", customer);
                model.addAttribute("stampCnt", customer.getStampCnt());
                model.addAttribute("couponCnt", customer.getCouponCnt());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "useCouponPayment";
    }
    @PostMapping("/directPayment")
    public String directPayment(@ModelAttribute UseCouponDTO useCouponDTO,
                                Model model) {
        try {
            String phone = useCouponDTO.getPhone();
            Customer customer = stampConfirmService.findCustomerByPhone(phone);

            // 항상 표시할 값
            Integer iceQty  = useCouponDTO.getOrderIceQty();
            Integer hotQty  = useCouponDTO.getOrderHotQty();
            Integer totalPrice = useCouponDTO.getOrderTotalPrice();

            model.addAttribute("phone", phone);
            model.addAttribute("iceQty", iceQty);
            model.addAttribute("hotQty", hotQty);
            model.addAttribute("totalPrice", totalPrice);

            // 쿠폰/스탬프 관련
            if (customer == null) {
                model.addAttribute("customer", null);
                model.addAttribute("stampCnt", 0);
                model.addAttribute("couponCnt", 0);
            } else {
                model.addAttribute("customer", customer);
                model.addAttribute("stampCnt", customer.getStampCnt());
                model.addAttribute("couponCnt", customer.getCouponCnt());
            }

            int useCouponCnt = 0;
            int discountPrice = 0;

            model.addAttribute("useCouponCnt", useCouponCnt);
            model.addAttribute("discountPrice", discountPrice);
            System.out.println(model.toString());
            return "useCouponPayment";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "결제 화면 준비 중 오류가 발생했습니다.");
            return "useCouponPayment";
        }
    }

}
