package com.example.todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.dto.TodoDto;

@SpringBootTest
public class TodoServiceTest {

    @Autowired
    private TodoService service;

    // Service <==> Repository 동작 확인
    // Serivce 가 잘 동작하는 지 확인
    @Test
    public void serviceList() {
        System.out.println(service.getList());
    }

    @Test
    public void serviceCreate() {
        TodoDto dto = new TodoDto();
        dto.setTitle("스프링 공부");
        dto.setImportant(true);

        System.out.println(service.create(dto));
    }

    @Test
    public void serviceRead() {
        System.out.println(service.getTodo(1L));

    }

}
