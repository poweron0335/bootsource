package com.example.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BasicController {

    @GetMapping("/basic")
    public String getMethodName() {
        return "반갑습니다";
    }

}
