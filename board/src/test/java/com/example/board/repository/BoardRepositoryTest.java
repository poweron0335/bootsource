package com.example.board.repository;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@gmail.com").build();

            Board board = Board.builder()
                    .title("title.." + i)
                    .content("content.." + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }

    @Transactional
    @Test
    public void readBoard() {
        Board board = boardRepository.findById(1L).get();
        System.out.println(board);
        // Board(bno=1, title=밍title1, content=밍content1,
        // writer=Member(email=user1@gmail.com, password=1111, name=USER1))
        System.out.println(board.getWriter());

    }

    @Test
    public void testList() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> list = boardRepository.list("tcw", "밍", pageable);
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
            // Board board = (Board) objects[0];
            // Member member = (Member) objects[1];
            // Long replyCtn = (Long) objects[2];

            // System.out.println(board + " " + member + " " + replyCtn);

        }
    }

    @Test
    public void testGetRow() {
        Object[] row = boardRepository.getRow(2L);
        System.out.println(Arrays.toString(row));

    }

    @Transactional // 두개의 작업이 하나의 작업으로 묶여야 하기 때문에 사용
    @Test
    public void testRemove() {

        // 자식(댓글) 삭제
        replyRepository.deleteByBno(5L);
        // 부모(원본글) 삭제
        boardRepository.deleteById(5L);
    }
}
