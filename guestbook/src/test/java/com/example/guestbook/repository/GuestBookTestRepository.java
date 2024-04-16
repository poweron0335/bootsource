package com.example.guestbook.repository;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.guestbook.entity.GuestBook;

@SpringBootTest
public class GuestBookTestRepository {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void insertTest() {
        // 300개 테스트 데이터 삽입
        IntStream.rangeClosed(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .title("밍 title" + i)
                    .writer("밍 writer" + i)
                    .content("밍 content" + (i % 10))
                    .build();

            guestBookRepository.save(guestBook);
        });
    }

    @Test
    public void testList() {
        // 전체 리스트 가져오기
        List<GuestBook> books = guestBookRepository.findAll();

        books.forEach(book -> {
            System.out.println(book);
        });
    }

    @Test
    public void testRow() {
        // 특정 Row 조회
        GuestBook guestBook = guestBookRepository.findById(1L).get();
        System.out.println(guestBook);

    }

    @Test
    public void testUpdate() {
        // 특정 Row 수정
        GuestBook guestBook = guestBookRepository.findById(1L).get();
        guestBook.setTitle("밍1");
        guestBook.setContent("밍내용1");

        guestBookRepository.save(guestBook);
    }

    @Test
    public void testDelete() {
        // 특정 Row 삭제
        guestBookRepository.deleteById(1L);

    }
}
