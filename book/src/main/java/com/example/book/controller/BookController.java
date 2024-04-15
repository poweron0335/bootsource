package com.example.book.controller;

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

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.service.BookService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping("/list")
    public void list(Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {

        log.info("list 화면 요청 ");

        PageResultDto<BookDto, Book> result = service.getList(requestDto);

        model.addAttribute("result", result);

    }

    // 페이지 나누기 이후 주소변경
    // read?id=174&page=1&type=&keyword=

    @GetMapping(value = { "/read", "/modify" })
    public void getRead(Long id, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {

        log.info("read 화면 요청 ");

        BookDto dto = service.getRow(id);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(BookDto updateDto, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {

        log.info("업데이트 요청 {} ", updateDto);
        log.info("page 나누기 정보 {} ", requestDto);

        Long id = service.bookUpdate(updateDto);

        // 주소줄에 따라 보내는 방식
        rttr.addAttribute("id", id);
        // 페이지 나누기 정보
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        // 조회화면으로 이동
        return "redirect:/book/read";

    }

    @GetMapping("/create")
    public void getCreate(BookDto bookDto, @ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("/book/create 요청");
        // valid 할 때 get 방식에 BookDto 무조건 보내줘야함

        // 테이블에 있는 카테고리 명 보여주기
        model.addAttribute("categories", service.categoryNameList());
    }

    @PostMapping("/create")
    public String postCreate(@Valid BookDto bookDto, BindingResult result, RedirectAttributes rttr, Model model,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {

        // @ModelAttribute 사용법 int page 일 경우 같이 딸려보내기 위해 사용
        log.info("create 화면 요청 ");

        if (result.hasErrors()) {
            model.addAttribute("categories", service.categoryNameList());
            return "/book/create";

        }

        // insert 작성
        Long id = service.bookCreate(bookDto);
        // 페이지 나누기 정보
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        rttr.addFlashAttribute("msg", id);

        return "redirect:/book/list";
    }

    @PostMapping("/delete")
    public String bookDelete(Long id, @ModelAttribute("requestDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {

        log.info("도서 삭제 요청 {}", id);
        service.bookDelete(id);

        // 페이지 나누기 정보
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/book/list";
    }

}
