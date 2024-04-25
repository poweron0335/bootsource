package com.example.board.service;

import com.example.board.dto.MemberDto;

public interface MemberService {
    // 회원가입
    public void register(MemberDto insertDto) throws Exception;

    // + 회원수정, 회원탈퇴 => default dtoToEntity, entityToDto 만들어서 사용

}
