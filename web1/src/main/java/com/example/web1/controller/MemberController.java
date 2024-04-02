package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.LoginDto;
import com.example.web1.dto.MemberDto;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public void login(LoginDto loginDto) {
        log.info("로그인 페이지 요청");
    }

    // @PostMapping("/login")
    // public void loginPost(String email, String name) {
    // log.info("로그인 정보 가져오기");
    // log.info("email {}", email);
    // log.info("name {}", name);
    // }

    // @PostMapping("/login")
    // public String loginPost(@ModelAttribute("mDto") LoginDto dto,
    // @ModelAttribute("page") int page, Model model) {
    // log.info("로그인 정보 가져오기");
    // log.info("email {}", dto.getEmail());
    // log.info("name {}", dto.getName());
    // log.info("page {}", page);

    // // model.addAttribute("page", page); == @ModelAttribute("page")

    // return "/member/info";
    // // 기본적으로 forward 방식을 사용
    // }

    // @Valid LoginDto : LoginDto 의 유효성 검사
    // post 에서만 가능
    // 유효성 검사 중 에러가 발생하면 BindingResult 에 자동으로 들어감
    @PostMapping("/login")
    public String loginPost(@Valid LoginDto mDto, BindingResult result) {
        log.info("로그인 정보 가져오기");
        log.info("email {}", mDto.getEmail());
        log.info("name {}", mDto.getName());

        // 유효성 검증을 통과하지 못한다면
        if (result.hasErrors()) {
            return "/member/login";

        }

        return "/member/info";
    }

    // 데이터 보내기
    // request.setAtrribute("이름", 값) == Model

    // /member/join + GET
    @GetMapping("/join")
    public void join(MemberDto memberDto) {
        log.info("/member/join 요청");
    }

    // /member/join + POST

    @PostMapping("/join")
    public String joinPost(@Valid MemberDto memberDto, BindingResult result) {

        if (result.hasErrors()) {
            return "/member/join";

        }
        return "redirect:/member/login";
    }

}
