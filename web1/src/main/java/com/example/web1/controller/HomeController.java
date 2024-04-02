package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
public class HomeController {

    // @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/")
    public String home() {
        // c.e.web1.controller.HomeController : home 요청 ==> log 로 인해 나옴
        log.info("home 요청"); // sout 와 같음

        // templates 아래 경로부터 시작 확장자 빼고 파일명만
        return "index";
    }

    // RedirectAttributes : redirect 시 데이터 전달
    // rttr.addAttribute("이름", 값); => 파라메터로 전달
    // rttr.addFlashAttribute("이름", 값) => // Session 을 이용(임시)해서 값을 저장

    @GetMapping("/ex3")
    public String ex3(RedirectAttributes rttr) {
        log.info("/ex3 요청");
        // response.sendRedirect("/qList.do")
        // path += "?bno="+bno;
        // return "redirect:/";
        // rttr.addAttribute("bno", 15); // http://localhost:8080/sample/basic?bno=15

        // Session 을 이용해서 값을 저장
        rttr.addFlashAttribute("bno", 15);

        return "redirect:/sample/basic"; // 무조건 경로지정(다른 컨트롤러에 있는 경로 포함해서)
    }

    // IllegalStateException: Ambiguous mapping => 전체 컨트롤러에서 동일한 매핑방식과 경로지정 시 뜨는 오류
    // @GetMapping("/ex3")
    // public void ex4() {
    // log.info("/ex3 요청");
    // }

}
