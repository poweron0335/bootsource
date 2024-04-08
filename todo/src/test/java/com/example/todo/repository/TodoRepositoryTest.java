package com.example.todo.repository;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.Todo;

@SpringBootTest
public class TodoRepositoryTest {
    // DAO == TodoRepository
    // serivce 에서 호출하는 메소드 테스트

    @Autowired
    private TodoRepository todoRepository;

    // todo 삽입

    @Test
    public void insertTest() {
        // IntStream.rangeClosed(1, 10).forEach(i -> {
        // Todo todo = Todo.builder()
        // .title("강아지 목욕 " + i)
        // .completed(false)
        // .important(true)
        // .build();
        // todoRepository.save(todo);
        // });
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Todo todo = Todo.builder()
                    .title("강아지 목욕 " + i)
                    .build();
            todoRepository.save(todo);
        });

    }

    // todo 전체 목록 조회
    @Test
    public void todoList() {
        todoRepository.findAll().forEach(todo -> {
            System.out.println(todo);
        });
    }

    // todo 완료 목록 조회
    @Test
    public void todoCompletedList() {
        todoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));

    }

    @Test
    public void todoImportantList() {
        todoRepository.findByImportant(true).forEach(todo -> System.out.println(todo));
    }

    // todo 전체 목록 조회
    @Test
    public void todoRow() {
        System.out.println(todoRepository.findById(1L));
    }

    // todo 수정
    @Test
    public void todoUpdate() {
        Todo todo = todoRepository.findById(1L).get();
        todo.setTitle("고길동");
        todo.setCompleted(true);
        todo.setImportant(false);
        System.out.println(todo);

        todoRepository.save(todo);
    }

    @Test
    public void todoDelete() {
        Todo todo = todoRepository.findById(2L).get();
        System.out.println(todo);
        todoRepository.delete(todo);
    }
}

// insert
// into
// todotbl
// (created_date, last_modified_date, title, todo_id)
// values
// (?, ?, ?, ?)