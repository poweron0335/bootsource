package com.example.jpa.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import javax.swing.text.html.Option;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.RoleType;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Member member = Member.builder()
                    .id("id" + i)
                    .userName("user" + i)
                    .age(i)
                    .roleType(RoleType.USER)
                    .description("user" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void readTest() {
        System.out.println(memberRepository.findById("id1"));
        System.out.println("-------------------------");
        memberRepository.findAll().forEach(member -> System.out.println(member));
        System.out.println("-----------------------------------------");
        // 특정이름을 조회
        memberRepository.findByUserName("마동석").forEach(member -> System.out.println(member));
    }

    @Test
    public void updateTest() {
        Optional<Member> result = memberRepository.findById("id2");

        result.ifPresent(member -> {
            member.setUserName("마동석");
            member.setAge(45);
            member.setRoleType(RoleType.ADMIN);
            System.out.println(memberRepository.save(member));
        });
    }

    @Test
    public void deleteTest() {
        Optional<Member> result = memberRepository.findById("id1");
        result.ifPresent(member -> {
            memberRepository.delete(member);
            System.out.println(memberRepository.save(member));
        });
    }
}
