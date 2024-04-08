package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Locker;
import com.example.jpa.entity.SportsMember;

@SpringBootTest
public class LockerRepositoryTest {

    @Autowired
    private SportMemberRepository sportMemberRepository;

    @Autowired
    private LockerRepository lockerRepository;

    @Test
    public void insertTest() {
        // Locker 삽입
        // LongStream.rangeClosed(12, 15).forEach(i -> {
        // Locker locker = Locker.builder().name("locker" + i).build();
        // lockerRepository.save(locker);
        // });

        // SportsMember 삽입
        LongStream.rangeClosed(16, 19).forEach(i -> {
            SportsMember sportsMember = SportsMember.builder().name("user" +
                    i).Locker(Locker.builder().id(i).build())
                    .build();
            sportMemberRepository.save(sportsMember);
        });
    }

    @Test
    public void readTest() {
        // 회원 조회 후 locker 정보 조회
        SportsMember sportsMember = sportMemberRepository.findById(1L).get();
        System.out.println(sportsMember);
        System.out.println("라커명 " + sportsMember.getLocker().getName());
        System.out.println("라커번호 " + sportsMember.getLocker().getId());
    }

    @Test
    public void updateTest() {
        SportsMember sportsMember = sportMemberRepository.findById(9L).get();
        sportsMember.setName("홍길동");
        sportMemberRepository.save(sportsMember);
    }

    @Test
    public void readTest2() {
        // locker 조회 후 회원 정보 조회
        Locker locker = lockerRepository.findById(1L).get();
        System.out.println(locker);
        System.out.println("회원 아이디 " + locker.getSportsMember().getId());
        System.out.println("회원명 " + locker.getSportsMember().getName());
    }

}
