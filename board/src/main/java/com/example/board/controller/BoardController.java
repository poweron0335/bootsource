package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.service.BoardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor

public class BoardController {

    private final BoardService service;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("list 화면 요청");

        model.addAttribute("result", service.getList(requestDto));
    }

    @GetMapping(value = { "/read", "/modify" })
    public void getRead(Long bno, Model model) {
        log.info("read 요청");

        BoardDto dto = service.getRow(bno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(BoardDto dto, RedirectAttributes rttr) {

        Long bno = service.boardUpdate(dto);

        rttr.addAttribute("bno", bno);

        return "redirect:/board/read";
    }

    @PostMapping("/delete")
    public String postDelete(Long bno) {

        service.removeWithReplied(bno);

        return "redirect:/board/list";
    }

}
