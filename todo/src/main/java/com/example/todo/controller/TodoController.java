package com.example.todo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
public class TodoController {

    // / 로 접속 시 list.html 보여지게
    @GetMapping(value = { "/", "/todo/list" })
    public String list() {
        log.info("list 화면 요청");

        return "/todo/list";
    }

}
