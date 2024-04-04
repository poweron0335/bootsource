package com.example.jpa.controller;

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

import com.example.jpa.dto.MemoDto;
import com.example.jpa.service.MemoServiceImpl;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor // Controller 에서 쓰는 건 Service 에서 생성한 객체를 주입한다는 의미
@Controller
@Log4j2
@RequestMapping("/memo")
public class MemoController {

    private final MemoServiceImpl service;

    // /memo/list + GET
    @GetMapping("/list")
    public void getMethodName(Model model) {
        log.info("list 페이지 요청");

        List<MemoDto> list = service.getMemoList();

        // list를 list.html 보여주고 싶다면? => Model 객체 사용
        model.addAttribute("list", list);

    }

    // /memo/read?mno=3 + GET
    // /memo/modify?mno=3 + GET : 수정을 위해 화면 보여주기
    @GetMapping({ "/read", "/modify" })
    public void read(@RequestParam Long mno, Model model) {
        log.info("read form 요청");

        MemoDto mDto = service.getMemo(mno);

        model.addAttribute("mDto", mDto);

        // 템플릿 찾기

    }

    // /memo/modify + POST : 실제 수정
    @PostMapping("/modify")
    public String modifyPost(MemoDto mDto, RedirectAttributes rttr) {

        log.info("modify 요청" + mDto);

        // 수정된 Dto 를 불러옴
        MemoDto updateDto = service.updateMemo(mDto);
        // redirect 로 값을 같이 보낼 때 씀
        rttr.addAttribute("mno", updateDto.getMno());
        // 수정 완료 시 /memo/read 이동
        return "redirect:/memo/read";
    }

    // /memo/delete?mno=3 + GET : 삭제 요청

    @GetMapping("/delete")
    public String deleteGet(@RequestParam Long mno) {

        log.info("메모 삭제 요청 {}", mno);
        service.deleteMemo(mno);

        // 삭제 성공 시 리스트
        return "redirect:/memo/list";
    }

    // /memo/create + GET : 입력을 위해 화면 보여주기

    @GetMapping("/create")
    public void createGet(@ModelAttribute("mDto") MemoDto mDto) {

        log.info("메모 입력 폼 요청");

    }

    // /memo/create + POST : 실제 입력요청
    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("mDto") MemoDto mDto, BindingResult result,
            RedirectAttributes rttr) {

        log.info("create Post 요청 {}", mDto);
        if (result.hasErrors()) {
            return "/memo/create";
        }

        service.insertMemo(mDto);

        // 세션을 이용해서 전달하는 방식
        rttr.addFlashAttribute("result", "SUCCESS");

        return "redirect:/memo/list";
    }

}
