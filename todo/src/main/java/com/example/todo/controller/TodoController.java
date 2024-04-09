package com.example.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.todo.dto.TodoDto;
import com.example.todo.service.TodoService;
import com.example.todo.service.TodoServiceImpl;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService service;

    // 멤버변수 초기화 - 1) 생성자 2) Setter 사용

    // / 로 접속 시 list.html 보여지게
    @GetMapping("/list")
    public String list(Model model) {
        log.info("list 화면 요청");

        // list 를 갖고옴
        List<TodoDto> list = service.getList();

        // model 에 담음
        model.addAttribute("list", list);

        // 템플릿에 전달됨
        return "/todo/list";
    }

    // todo/read?id=11 요청 처리 컨트롤러
    @GetMapping("/read")
    public void read(@RequestParam Long id, Model model) {
        log.info("read 화면 요청 {}", id);

        TodoDto dto = service.getTodo(id);

        model.addAttribute("dto", dto);
    }

    @GetMapping("/create")
    public void getCreate() {
        log.info("create 화면 요청");
    }

    @PostMapping("/create")
    public String postCreate(TodoDto dto, RedirectAttributes rttr) {

        log.info("create post 요청 {}", dto);

        TodoDto result = service.create(dto);

        rttr.addFlashAttribute("msg", result.getId());

        return "redirect:/todo/list";
    }

    // /todo/done 완료 목록 처리
    @GetMapping("/done")
    public void getDoneList(Model model) {

        log.info("완료 목록 요청");

        List<TodoDto> list = service.getCompletedList();

        model.addAttribute("list", list);

    }

    // /todo/update
    @PostMapping("/update")
    public String postUpdate(Long id, RedirectAttributes rttr) {
        // id 값 받기
        Long id2 = service.todoUpdate(id);

        rttr.addAttribute("id", id2);
        // /read
        return "redirect:/todo/read";
    }

    @PostMapping("/delete")
    public String postDelete(Long id) {

        service.todoDelete(id);

        return "redirect:/todo/list";
    }

}
