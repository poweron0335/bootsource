package com.example.movie.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movie.dto.AuthMemberDto;
import com.example.movie.dto.MemberDto;
import com.example.movie.dto.PasswordChangeDto;
import com.example.movie.entity.Member;
import com.example.movie.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MovieUserServiceImpl implements UserDetailsService, MovieUserService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

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

    @Transactional
    @Override
    public void nickNameUpdate(MemberDto upMemberDto) {
        // 1) select 2) select 결과에 따라 insert or update
        // memberRepository.save(dtoToEntity(upMemberDto));

        // save 를 사용 안하고 바로 update 할 경우
        memberRepository.updateNickName(upMemberDto.getNickname(), upMemberDto.getEmail());
    }

    @Override
    public void passwordUpdate(PasswordChangeDto passwordChangeDto) throws IllegalStateException {
        // 현재 이메일과 비밀번호 일치 여부 => true => 비밀번호 변경
        // select => 결과가 있다면 => update

        Member member = memberRepository.findByEmail(passwordChangeDto.getEmail()).get();

        // member 에서 가지고 나올 때 비밀번호는 암호화된 상태

        // passwordEncoder.encode(1111) : 암호화 시 사용
        // passwordEncoder.matches(rawPassword, encodePassword) : rawPassword = 1111,
        // encodePassword = 암호화된 비밀번호
        if (!passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), member.getPassword())) {
            throw new IllegalStateException("현재 비밀번호가 다릅니다.");
        } else {
            member.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
            memberRepository.save(member);
        }
    }

}