package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.MemberDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/login")
    public void member() {
        log.info("로그인 페이지 요청");
    }

    // @PostMapping("/login")
    // public void loginPost(String email, String name) {
    // log.info("로그인 정보 가져오기");
    // log.info("email {}", email);
    // log.info("name {}", name);
    // }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("mDto") MemberDto dto, @ModelAttribute("page") int page, Model model) {
        log.info("로그인 정보 가져오기");
        log.info("email {}", dto.getEmail());
        log.info("name {}", dto.getName());
        log.info("page {}", page);

        // model.addAttribute("page", page); == @ModelAttribute("page")

        return "/member/info";
        // 기본적으로 forward 방식을 사용
    }

    // 데이터 보내기
    // request.setAtrribute("이름", 값) == Model

}
