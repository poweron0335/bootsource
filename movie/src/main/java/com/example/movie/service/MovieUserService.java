package com.example.movie.service;

import java.util.Optional;

import com.example.movie.dto.MemberDto;
import com.example.movie.dto.PasswordChangeDto;
import com.example.movie.entity.Member;

public interface MovieUserService {

    // 회원가입
    String register(MemberDto insertDto) throws IllegalStateException;

    // 닉네임 수정
    void nickNameUpdate(MemberDto upMemberDto);

    // 비밀번호 수정
    void passwordUpdate(PasswordChangeDto passwordChangeDto) throws IllegalStateException;

    // 회원탈퇴
    void leave(MemberDto leaveMemberDto);

    // dto => entity
    public default Member dtoToEntity(MemberDto dto) {

        return Member.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .mid(dto.getMid())
                .memberRole(dto.getRole())
                .password(dto.getPassword())
                .build();

    }

    // entity => dto

    public default MemberDto entityToDto(Member member) {

        return MemberDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .password(member.getPassword())
                .mid(member.getMid())
                .role(member.getMemberRole())
                .build();
    }
}