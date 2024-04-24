package com.example.board.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.constant.MemberRole;
import com.example.board.dto.MemberAuthDto;
import com.example.board.dto.MemberDto;
import com.example.board.entity.Member;
import com.example.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService, MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // id == email == username
        Optional<Member> result = memberRepository.findById(username);

        if (!result.isPresent())
            throw new UsernameNotFoundException("이메일을 확인해 주세요");

        Member member = result.get();

        // entity => dto
        // 시큐리티 로그인 ==> 회원 정보 + 허가와 관련된 정보(사이트를 접근 여부)
        MemberDto memberDto = MemberDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .memberRole(member.getMemberRole())
                .build();

        return new MemberAuthDto(memberDto);
    }

    @Override
    public void register(MemberDto insertDto) {

        log.info("서비스 회원가입 요청 {}", insertDto);

        try {
            // 중복 이메일
            validateDuplicationMember(insertDto.getEmail());
        } catch (Exception e) {
            throw e;
        }

        // select => 존재시 / 없으면
        // update 실행 / insert
        Member member = Member.builder()
                .email(insertDto.getEmail())
                .name(insertDto.getName())
                .password(passwordEncoder.encode(insertDto.getPassword()))
                .memberRole(MemberRole.MEMBER)
                .build();

        memberRepository.save(member);

    }

    private void validateDuplicationMember(String email) {
        Optional<Member> member = memberRepository.findById(email);
        if (member.isPresent())
            throw new IllegalStateException("이미 가입된 회원입니다.");
    }

}