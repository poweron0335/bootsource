package com.example.jpa.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
public class HomeController {
    // memo/home 보여주기
    @GetMapping("/")
    public String home() {
        log.info("home 페이지 요청");
        return "/memo/home";
    }

}
