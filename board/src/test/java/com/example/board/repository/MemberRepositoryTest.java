package com.example.board.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.board.constant.MemberRole;
import com.example.board.entity.Member;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert() {
        // 멤버 삽입
        IntStream.rangeClosed(11, 20).forEach(i -> {
            Member member = Member.builder()

                    .email("user" + i + "@naver.com")
                    .name("USER" + i)
                    .password(passwordEncoder.encode("1111"))
                    .memberRole(MemberRole.MEMBER)
                    .build();

            memberRepository.save(member);
        });
    }

}
