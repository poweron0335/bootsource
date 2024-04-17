package com.example.board.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PageRequestDto {

    private int page;

    private int size;

    private String type;

    private String keyword;

    public PageRequestDto() {
        // page 값이 안 들어온다면 기본값이 1
        this.page = 1;
        // size 값이 안 들어온다면 기본값이 10
        this.size = 10;
    }

    // 스프링 페이지 나누기 정보 저장 => Pageable
    // 1 page => 0 부터 시작
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }

}
