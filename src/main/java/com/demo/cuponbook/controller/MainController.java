package com.demo.cuponbook.controller;

import com.demo.cuponbook.dto.StampSaveDTO;
import com.demo.cuponbook.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MainController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/stamp")
    public String index(@RequestBody StampSaveDTO stampSaveDTO, Model model) {
        try {
            customerService.collectStamp(stampSaveDTO);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "stamp";
    }
}
