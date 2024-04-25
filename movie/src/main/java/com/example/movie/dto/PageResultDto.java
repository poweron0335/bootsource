package com.example.movie.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
// Generics 를 활용하여 공통 타입의 객체를 생성
public class PageResultDto<DTO, EN> {
    // entity 타입의 리스트를 dto 타입 리스트로 변환
    // List<TodoDto> compList 와 동일한 코드
    private List<DTO> dtoList;

    // 화면에서 시작 페이지 번호

    // 화면에서 끝 페이지 번호
    private int start, end;
    // 이전 / 다음 이동 링크 여부
    private boolean prev, next;

    // 현재 페이지 번호
    private int page;

    // 총 페이지 번호
    private int totalPage;

    // 목록 사이즈
    private int size;

    // 페이지 번호 목록
    private List<Integer> pageList;

    // Page<EN> result : 스프링에서 페이지 나누기와 관련된 모든 정보를 가지고 있는 객체
    // Function<EN, DTO> fn : entity => dto 로 변환해주는 메소드 사용
    // fn : todo -> entityToDto(todo) 와 동일한 코드
    public PageResultDto(Page<EN> result, Function<EN, DTO> fn) {
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());

        this.totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    public void makePageList(Pageable pageable) {

        // spring 에서 page는 0 부터 시작하기 때문에 + 1 을 함
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        // Math.ceil(1 / 10.0) = 0.1 * 10
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;

        // tempEnd = 10 이기 때문에 - 9 해줌
        this.start = tempEnd - 9;

        // totalPage 보다 크면 tempEnd 쓰고 아니면 totalPage 사용
        this.end = totalPage > tempEnd ? tempEnd : totalPage;

        this.prev = start > 1;

        this.next = totalPage > tempEnd;

        // List<Integer>
        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
