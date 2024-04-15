package com.example.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.book.dto.PageRequestDto;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class HomeController {

    @GetMapping("/")
    public String home(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("home 화면 요청");

        return "home";
    }
}
