package com.demo.cuponbook.controller;

import lombok.RequiredArgsConstructor;

import com.demo.cuponbook.service.UserService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

}
