package com.example.todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TodoServiceTest {

    @Autowired
    private TodoServiceImpl service;

    // Service <==> Repository 동작 확인
    // Serivce 가 잘 동작하는 지 확인
    @Test
    public void serviceList() {
        System.out.println(service.getList());
    }

}
