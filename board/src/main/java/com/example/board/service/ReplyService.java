package com.example.board.service;

import java.util.List;

import com.example.board.dto.ReplyDto;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;

public interface ReplyService {

    List<ReplyDto> getReplies(Long bno);

    Long create(ReplyDto dto);

    Long update(ReplyDto dto);

    void remove(Long rno);

    ReplyDto getReply(Long rno);

    // entity => dto
    public default ReplyDto entityToDto(Reply reply) {
        return ReplyDto.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .bno(reply.getBoard().getBno())
                .createdDate(reply.getCreatedDate())
                .lastModifiedDate(reply.getLastModifiedDate())
                .build();
    }

    public default Reply dtoToEntity(ReplyDto dto) {
        // 찾아오기
        Board board = Board.builder().bno(dto.getBno()).build();
        return Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .build();
    }
}
