package com.example.movie.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.movie.dto.AuthMemberDto;
import com.example.movie.dto.MemberDto;
import com.example.movie.entity.Member;
import com.example.movie.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MovieUserServiceImpl implements UserDetailsService, MovieUserService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 요청 {}", username);

        // 시큐리티에서 사용하는 로그인 메소드
        // User 로 리턴 or User 구현한 CustomUser 로 리턴
        // Optional<Member> result = memberRepository.findById(null);

        // if (result.isPresent())
        // Member member = result.get();

        // return User.builder()
        // .username(member.getEmail())
        // .password(member.getPassword())
        // .roles(member.getMemberRole().toString())
        // .build();

        Optional<Member> result = memberRepository.findByEmail(username);

        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Check Email");
        }
        Member member = result.get();
        // entity => dto

        return new AuthMemberDto(entityToDto(member));
    }

    @Override
    public String register(MemberDto insertDto) throws IllegalStateException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public void nickNameUpdate(MemberDto upMemberDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nickNameUpdate'");
    }

    @Override
    public void passwordUpdate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'passwordUpdate'");
    }

}