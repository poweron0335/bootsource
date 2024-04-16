package com.example.guestbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.entity.QGuestBook;
import com.example.guestbook.repository.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class GuestBookServiceImpl implements GuestBookSerivce {

    private final GuestBookRepository guestBookRepository;

    // @Override
    // public List<GuestBookDto> getList() {

    // List<GuestBook> entites =
    // guestBookRepository.findAll(Sort.by("gno").descending());

    // // List<GuestBook> guestList = new ArrayList<>();
    // // list.forEach(guest -> guestList.add(entityToDto(guest)));

    // Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));
    // return entites.stream().map(fn).collect(Collectors.toList());
    // }

    // 페이지 나누기 후
    @Override
    public PageResultDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("gno").descending());
        // Page<GuestBook> result = guestBookRepository.findAll(pageable);

        BooleanBuilder builder = getSearch(requestDto);
        // querydsl.QuerydslPredicateExecutor.findAll
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<GuestBookDto, GuestBook>(result, fn);
    }

    @Override
    public GuestBookDto getRow(Long gno) {

        GuestBook guestBook = guestBookRepository.findById(gno).get();

        return entityToDto(guestBook);
    }

    @Override
    public Long guestBookUpdate(GuestBookDto updateDto) {

        GuestBook entity = guestBookRepository.findById(updateDto.getGno()).get();

        entity.setTitle(updateDto.getTitle());
        entity.setContent(updateDto.getContent());

        GuestBook updateGuestBook = guestBookRepository.save(entity);

        return updateGuestBook.getGno();
    }

    @Override
    public void guestBookDelete(Long gno) {
        GuestBook guestBook = guestBookRepository.findById(gno).get();

        guestBookRepository.delete(guestBook);
    }

    @Override
    public Long guestBookCreate(GuestBookDto dto) {
        GuestBook guestBook = dtoToEntity(dto);

        GuestBook newGeustBook = guestBookRepository.save(guestBook);
        return newGeustBook.getGno();
    }

    // Book 프로젝트에서 BookRepository 에 작성함
    private BooleanBuilder getSearch(PageRequestDto requestDto) {

        BooleanBuilder builder = new BooleanBuilder();

        // Q 클래스 사용
        QGuestBook guestBook = QGuestBook.guestBook;

        // type 가져오기
        String type = requestDto.getType();

        // keyword 가져오기
        String keyword = requestDto.getKeyword();

        // where gno > 0 and title like '%Title%' or contetn like '%content%'
        // gno > 0
        builder.and(guestBook.gno.gt(0L));

        if (type == null || type.trim().length() == 0) {
            return builder;

        }

        // 검색 타입이 있는 경우
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(guestBook.title.contains(keyword));
        }

        if (type.contains("c")) {
            conditionBuilder.or(guestBook.content.contains(keyword));
        }

        if (type.contains("w")) {
            conditionBuilder.or(guestBook.writer.contains(keyword));
        }
        builder.and(conditionBuilder);

        return builder;
    }

}
