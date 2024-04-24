package com.example.board.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.dto.PageResultDto;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepository;
import com.example.board.repository.ReplyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Override
    public PageResultDto<BoardDto, Object[]> getList(PageRequestDto requestDto) {

        Page<Object[]> result = boardRepository.list(requestDto.getType(), requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("bno").descending()));

        Function<Object[], BoardDto> fn = (entity -> entityToDto((Board) entity[0], (Member) entity[1],
                (Long) entity[2]));
        // return result.stream().map(fn).collect(Collectors.toList());
        return new PageResultDto<>(result, fn);
    }

    @Override
    public BoardDto getRow(Long bno) {
        Object[] row = boardRepository.getRow(bno);
        return entityToDto((Board) row[0], (Member) row[1], (Long) row[2]);
    }

    @Override
    public void modify(BoardDto dto) {

        Board board = boardRepository.findById(dto.getBno()).get();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());

        boardRepository.save(board);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        // 자식(댓글) 삭제
        replyRepository.deleteByBno(bno);
        // 부모(원본글) 삭제
        boardRepository.deleteById(bno);
    }

    @Override
    public Long create(BoardDto dto) {

        Optional<Member> member = memberRepository.findById(dto.getWriterEmail());

        if (member.isPresent()) {
            Board entity = Board.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .writer(member.get())
                    .build();
            entity = boardRepository.save(entity);
            return entity.getBno();
        }
        return null;

    }

}