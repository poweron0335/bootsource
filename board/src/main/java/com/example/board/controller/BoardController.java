package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.dto.BoardDto;

import com.example.board.dto.PageRequestDto;
import com.example.board.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService service;

    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("list 요청");

        model.addAttribute("result", service.getList(requestDto));
    }

    @GetMapping({ "/read", "/modify" })
    public void getRow(@RequestParam Long bno, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        model.addAttribute("dto", service.getRow(bno));
    }

    @PreAuthorize("authentication.name ==  #dto.writerEmail")
    @PostMapping("/modify")
    public String postModify(BoardDto dto, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {

        service.modify(dto);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        rttr.addAttribute("bno", dto.getBno());
        return "redirect:/board/read";
    }

    @PreAuthorize("authentication.name ==  #writerEmail")
    @PostMapping("/remove")
    public String postMethodName(Long bno, String writerEmail, @ModelAttribute("requestDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {

        service.removeWithReplies(bno);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public void getMethodName(BoardDto boardDto, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("글 작성 폼 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postMethodName(@Valid BoardDto boardDto, BindingResult result,
            @ModelAttribute("requestDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {

        log.info("글 등록 " + boardDto);

        service.create(boardDto);

        if (result.hasErrors()) {
            return "/board/create";
        }

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/board/list";
    }

}