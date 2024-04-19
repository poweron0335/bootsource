package com.example.board.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.entity.Board;
import com.example.board.entity.Reply;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long bno = (long) (Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("밍reply" + i)
                    .replyer("밍guest" + i)
                    .board(board)
                    .build();

            replyRepository.save(reply);
        });
    }

    @Transactional
    @Test
    public void getRow() {

        Reply reply = replyRepository.findById(2L).get();
        System.out.println(reply); // Reply(rno=2, text=밍reply2, replyer=밍guest2)

        // FetchType.LAZY 이기 때문에 reply 부모 게시물은 안 갖고옴
        System.out.println(reply.getBoard());
    }

    @Test
    public void getReplies() {

        Board board = Board.builder().bno(94L).build();
        // Select * from reply r where r.board_bno = 100;
        List<Reply> replies = replyRepository.getRepliesByBoardOrderByRno(board);

        System.out.println(replies.toString());
    }
}
