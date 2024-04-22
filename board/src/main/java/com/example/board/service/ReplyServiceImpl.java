package com.example.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.board.dto.ReplyDto;
import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import com.example.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    @Override
    public List<ReplyDto> getReplies(Long bno) {

        Board board = Board.builder().bno(bno).build();
        // Select * from reply r where r.board_bno = 100;
        List<Reply> replies = replyRepository.getRepliesByBoardOrderByRno(board);

        // entity 리스트 ==> dto 리스트
        return replies.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
    }

    @Override
    public Long create(ReplyDto dto) {

        return replyRepository.save(dtoToEntity(dto)).getRno();
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public ReplyDto getReply(Long rno) {
        Reply reply = replyRepository.findById(rno).get();

        return entityToDto(reply);
    }

    @Override
    public Long update(ReplyDto dto) {
        Reply entity = replyRepository.findById(dto.getRno()).get();

        entity.setText(dto.getText());

        Reply updateText = replyRepository.save(entity);

        return updateText.getRno();
    }

}
