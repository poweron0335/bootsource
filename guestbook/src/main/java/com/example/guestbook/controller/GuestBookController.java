package com.example.guestbook.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.service.GuestBookSerivce;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/guestbook")
public class GuestBookController {

    private final GuestBookSerivce serivce;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        // log.info("list 요청");

        PageResultDto<GuestBookDto, GuestBook> result = serivce.getList(requestDto);

        model.addAttribute("result", result);

    }

    @GetMapping(value = { "/read", "/modify" })
    public void getRead(Long gno, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("read or modify 요청");

        GuestBookDto dto = serivce.getRow(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(@ModelAttribute("reqeustDto") PageRequestDto requestDto, GuestBookDto updateDto,
            RedirectAttributes rttr) {

        Long gno = serivce.guestBookUpdate(updateDto);

        rttr.addAttribute("gno", gno);
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/delete")
    public String postDelete(Long gno, @ModelAttribute("reqeustDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {

        log.info("삭제 요청 {} ", gno);
        serivce.guestBookDelete(gno);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/guestbook/list";
    }

    @GetMapping("/create")
    public void getCreate(GuestBookDto dto, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("/book/create 요청");

    }

    @PostMapping("/create")
    // BindingResult가 RedirectAttributes보다 앞에 위치하여 유효성 검사 결과를 처리하고, 리다이렉트 후 데이터를 전달
    public String postCreate(@Valid GuestBookDto dto, BindingResult result, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("create 화면 요청 {}", dto);

        if (result.hasErrors()) {
            return "/guestbook/create";
        }

        Long gno = serivce.guestBookCreate(dto);

        rttr.addFlashAttribute("msg", gno);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/guestbook/list";

    }

}
