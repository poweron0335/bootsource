package com.example.book.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequestMapping("/book")
public class BookController {

    @GetMapping("/list")
    public String getList() {

        log.info("list 화면 요청 ");

        return "/book/list";
    }

    @GetMapping("/read")
    public String getRead() {

        log.info("read 화면 요청 ");

        return "/book/read";
    }

    @GetMapping("/modify")
    public String getModifiy() {

        log.info("modify 화면 요청 ");

        return "/book/modify";
    }

    @GetMapping("/create")
    public String getCreate() {

        log.info("create 화면 요청 ");

        return "/book/create";
    }

}
