package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movie.dto.AuthMemberDto;
import com.example.movie.dto.MemberDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PasswordChangeDto;
import com.example.movie.service.MovieUserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RequestMapping("/member")
@Log4j2
@Controller
public class MemberController {

    private final MovieUserService service;

    @GetMapping("/login")
    public void getLogin(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("로그인 폼 요청");
    }

    // /profile => profile.hmtl 보여주기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public void getProfile(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("profile 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public String getEditForm(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        return "/member/edit-profile";
    }

    // /edit/nickname
    @PostMapping("/edit/nickname")
    public String postNickName(MemberDto memberDto, HttpSession session) {

        service.nickNameUpdate(memberDto);
        // SecurityContent 안에 저장된 Authentication 변경되지 않음
        // 1) 현재 세션 제거 => 재로그인
        // session.invalidate();
        // return "redirect:/member/login";

        // 2) Authentication 업데이트
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        // authentication 에 pricipal 을 가져오고 authMemberDto 객체에 담고 형변환
        AuthMemberDto authMemberDto = (AuthMemberDto) authentication.getPrincipal();

        // aauthMemberDto 안에 nickname 을 변경한 닉네임이 담긴 memberDto 에서 갖고옴
        authMemberDto.getMemberDto().setNickname(memberDto.getNickname());

        // SecurityContextHolder 에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/member/profile";
    }

    // /edit/password

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/password")
    public String postPassword(PasswordChangeDto passwordChangeDto, HttpSession session, RedirectAttributes rttr) {

        try {
            service.passwordUpdate(passwordChangeDto);

        } catch (Exception e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/edit";

        }
        session.invalidate();
        return "redirect:/member/login";

    }

}