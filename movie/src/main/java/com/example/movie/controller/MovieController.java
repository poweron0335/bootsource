package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movie.dto.MovieDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PageResultDto;
import com.example.movie.service.MovieService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping("/list")
    public void list(@ModelAttribute("requestDto") PageRequestDto pageRequestDto, Model model) {
        log.info("list 요청");

        PageResultDto<MovieDto, Object[]> result = service.getList(pageRequestDto);

        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getMovie(@RequestParam Long mno, Model model,
            @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("영화 상세 정보 요청 {}", mno);

        model.addAttribute("dto", service.getRow(mno));
    }

    @PostMapping("/modify")
    public String postModify(MovieDto movieDto, @ModelAttribute("requestDto") PageRequestDto pageRequestDto,
            RedirectAttributes rttr) {
        log.info("movie 수정 요청 {}", movieDto);

        Long mno = service.movieUpdate(movieDto);

        rttr.addAttribute("mno", mno);
        rttr.addAttribute("page", pageRequestDto.getPage());
        rttr.addAttribute("type", pageRequestDto.getType());
        rttr.addAttribute("keyword", pageRequestDto.getKeyword());
        rttr.addAttribute("size", pageRequestDto.getSize());

        return "redirect:/movie/read";
    }

    @PostMapping("/remove")
    public String postRemove(Long mno, @ModelAttribute("requestDto") PageRequestDto pageRequestDto,
            RedirectAttributes rttr) {

        service.movieRemove(mno);
        rttr.addAttribute("page", pageRequestDto.getPage());
        rttr.addAttribute("type", pageRequestDto.getType());
        rttr.addAttribute("keyword", pageRequestDto.getKeyword());
        rttr.addAttribute("size", pageRequestDto.getSize());

        return "redirect:/movie/list";
    }

    @GetMapping("/register")
    public void getMethodName(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("영화 등록 폼 요청 ");

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public String postRegister(MovieDto movieDto, @ModelAttribute("requestDto") PageRequestDto pageRequestDto,
            RedirectAttributes rttr) {
        log.info("영화 등록 {} ", movieDto);

        // 서비스 호출
        Long mno = service.movieInsert(movieDto);

        // mno 넘기기
        rttr.addFlashAttribute("msg", mno);
        rttr.addAttribute("page", pageRequestDto.getPage());
        rttr.addAttribute("type", pageRequestDto.getType());
        rttr.addAttribute("keyword", pageRequestDto.getKeyword());
        rttr.addAttribute("size", pageRequestDto.getSize());

        return "redirect:/movie/list";

    }

}