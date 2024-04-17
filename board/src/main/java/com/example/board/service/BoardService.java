package com.example.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.dto.PageResultDto;
import com.example.board.entity.Board;
import com.example.board.entity.Member;

@Service
public interface BoardService {

    PageResultDto<BoardDto, Object[]> getList(PageRequestDto requestDto);

    BoardDto getRow(Long bno);

    Long boardUpdate(BoardDto dto);

    void removeWithReplied(Long bno);

    // entity => dto
    public default BoardDto entityToDto(Board board, Member member, Long replyCount) {
        return BoardDto.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .content(board.getContent())
                .replyCount(replyCount != null ? replyCount : 0)
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getLastModifiedDate())
                .build();
    }

    public default Board dtoToEntity(BoardDto dto) {
        // 찾아오기
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        return Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
    }
}
