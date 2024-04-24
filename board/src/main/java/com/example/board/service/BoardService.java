package com.example.board.service;

import java.util.List;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.dto.PageResultDto;
import com.example.board.entity.Board;
import com.example.board.entity.Member;

public interface BoardService {

    PageResultDto<BoardDto, Object[]> getList(PageRequestDto requestDto);

    BoardDto getRow(Long bno);

    void modify(BoardDto dto);

    void removeWithReplies(Long bno);

    Long create(BoardDto dto);

    // entity => dto
    public default BoardDto entityToDto(Board board, Member member, Long replyCount) {

        return BoardDto.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount != null ? replyCount : 0)
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build();
    }

    // dto => entity
    public default Board dtoToEntity(BoardDto dto) {

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        return Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
    }
}