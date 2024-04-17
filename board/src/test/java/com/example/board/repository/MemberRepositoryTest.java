package com.example.board.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.entity.Member;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testInsert() {
        // 멤버 삽입
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()

                    .email("user" + i + "@gmail.com")
                    .name("USER" + i)
                    .password("1111")
                    .build();

            memberRepository.save(member);
        });
    }
}
