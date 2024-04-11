package com.example.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.dto.BookDto;
import com.example.book.service.BookService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BookSerivceTest {

    @Autowired
    private BookService service;

    @Transactional
    @Test
    public void serviceList() {
        System.out.println(service.getList());
    }

}
