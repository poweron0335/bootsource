package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor // == @Autowired private TodoRepository todoRepository;
@Service
@Log4j2
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public List<TodoDto> getList() {
        // 미완료 목록
        List<Todo> list = todoRepository.findByCompleted(false);
        // Todo => TodoDto 변환
        // List<TodoDto> todoList = new ArrayList<>();
        // list.forEach(todo -> todoList.add(entityToDto(todo)));

        List<TodoDto> todoList = list.stream().map(todo -> entityToDto(todo)).collect(Collectors.toList());
        return todoList;
    }

    @Override
    public TodoDto create(TodoDto dto) {
        // TodoDto => Todo 변환
        Todo entity = todoRepository.save(dtoToEntity(dto));

        return entityToDto(entity);
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo entity = todoRepository.findById(id).get();

        return entityToDto(entity);
    }

    @Override
    public List<TodoDto> getCompletedList() {
        // completed 필드 값에 true 인 모든 Todo 객체를 result 에 담는다.
        List<Todo> result = todoRepository.findByCompleted(true);

        // compList 라는 List 객체를 만들기
        // List<TodoDto> compList = new ArrayList<>();

        // result list 에 todo 객체를 DTO(compList) 로 변환 후 결과를 compList 에 추가
        // result.forEach(todo -> compList.add(entityToDto(todo)));

        List<TodoDto> compList = result.stream().map(todo -> entityToDto(todo)).collect(Collectors.toList());
        // compList 를 리턴 받음
        return compList;
    }

    @Override
    public Long todoUpdate(Long id) {
        // 업데이트 완료 후 id 리턴
        Todo entity = todoRepository.findById(id).get();

        entity.setCompleted(true);

        Todo todo = todoRepository.save(entity);
        return todo.getId();
    }

    @Override
    public void todoDelete(Long id) {

        todoRepository.deleteById(id);
    }

}
