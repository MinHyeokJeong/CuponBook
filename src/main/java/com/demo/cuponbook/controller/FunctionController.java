package com.demo.cuponbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FunctionController {


    @GetMapping("/showStamp")
    public String StampShow() {

        return "showStamp";
    }

    @PostMapping("/")
    public String StampAccumulate() {
        //스탬프 누적

        return "";
    }

}
