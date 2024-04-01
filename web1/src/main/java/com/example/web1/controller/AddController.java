package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.AddDto;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/calc")
public class AddController {

    @GetMapping("/add")
    public void addGet() {
        log.info("/calc/add 요청");
    }

    // 사용자 입력 값 가져오기
    // 1. HttpServletRequest req
    // 2. 파라메터 이용(폼 이름과 변수명 일치시키기)
    // 3. DTO 이용
    // - post 컨트롤러 응답 후 보여지는 화면단에서 dto 에 들어 있는 값을 사용 가능

    // @PostMapping("/add")
    // public void addPost(HttpServletRequest req) {
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", req.getParameter("num1"));
    // log.info("num2 {}", req.getParameter("num2"));
    // }

    // spring boot 의 편한점
    // 1. 형변환을 자동으로 해줌
    // @PostMapping("/add")
    // public void addPost(@RequestParam(value = "op1", defaultValue = "0") int
    // num1,
    // @RequestParam(value = "op2", defaultValue = "0") int num2) { // html 에서 선언한
    // 변수명과 일치해야함
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", num1);
    // log.info("num2 {}", num2);

    // }

    @PostMapping("/add")
    public void addPost(AddDto dto, Model model) {
        log.info("/calc/add post 요청");
        log.info("num1 {}", dto.getNum1());
        log.info("num2 {}", dto.getNum2());

        // dto.setResult(dto.getNum1() + dto.getNum2());
        model.addAttribute("result", dto.getNum1() + dto.getNum2());
    }

    // 데이터 보내기
    // request.setAttribute("이름",값) == Model

    @GetMapping("/rules")
    public void rulesGet() {
        log.info("/calc/rules 요청");
    }

    @PostMapping("/rules")
    public String rulesPost(AddDto addDto, @ModelAttribute("op") String op, Model model) {
        log.info("/calc/rules post 요청");

        int result = 0;
        switch (op) {
            case "+":
                result = addDto.getNum1() + addDto.getNum2();
                break;
            case "-":
                result = addDto.getNum1() - addDto.getNum2();
                break;
            case "*":
                result = addDto.getNum1() * addDto.getNum2();
                break;
            case "/":
                result = addDto.getNum1() / addDto.getNum2();
                break;
        }
        // model.addAttribute("result", result);
        addDto.setResult(result);

        return "/calc/result";
    }

}
