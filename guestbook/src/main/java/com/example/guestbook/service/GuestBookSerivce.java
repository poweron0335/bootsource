package com.example.guestbook.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;

import lombok.RequiredArgsConstructor;

@Service
public interface GuestBookSerivce {

    // public List<GuestBookDto> getList();

    // 페이지 나누기 후
    // PageResultDto<DTO, EN>
    PageResultDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto);

    GuestBookDto getRow(Long gno);

    Long guestBookUpdate(GuestBookDto updateDto);

    void guestBookDelete(Long gno);

    Long guestBookCreate(GuestBookDto dto);

    // entity => dto
    public default GuestBookDto entityToDto(GuestBook guestBook) {
        return GuestBookDto.builder()
                .gno(guestBook.getGno())
                .title(guestBook.getTitle())
                .writer(guestBook.getWriter())
                .content(guestBook.getContent())
                .createdDate(guestBook.getCreatedDate())
                .lastModifiedDate(guestBook.getLastModifiedDate())
                .build();
    }

    public default GuestBook dtoToEntity(GuestBookDto dto) {
        return GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .content(dto.getContent())
                .build();
    }
}
