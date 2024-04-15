package com.example.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;
import com.example.book.repository.BookRepository;
import com.example.book.repository.CategoryRepository;
import com.example.book.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    // 페이지 나누기 전
    // @Override
    // public List<BookDto> getList() {

    // List<Book> list = bookRepository.findAll(Sort.by("id").descending());

    // // List<BookDto> bookList = new ArrayList<>();
    // // list.forEach(book -> bookList.add(entityToDto(book)));
    // List<BookDto> bookDtos = list.stream().map(book ->
    // entityToDto(book)).collect(Collectors.toList());

    // return bookDtos;
    // }

    // 페이지 나누기 후
    @Override
    public PageResultDto<BookDto, Book> getList(PageRequestDto requestDto) {
        // Sort 객체만 뽑아내면 됨
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());

        // Page : 페이지 나누기에 필요한 메소드 제공
        // == PageDto와 같은 역할
        Page<Book> result = bookRepository
                // BookRepository 에서 선언한 type 과 keyword 를 불러옴
                .findAll(bookRepository.makePredicate(requestDto.getType(), requestDto.getKeyword()), pageable);
        Function<Book, BookDto> fn = (enity -> entityToDto(enity));
        return new PageResultDto<>(result, fn);
    }

    @Override
    public Long bookCreate(BookDto dto) {

        Category category = categoryRepository.findByName(dto.getCategoryName()).get();
        Publisher publisher = publisherRepository.findByName(dto.getPublisherName()).get();

        // BookDto 를 Book 으로 변환
        Book book = dtoToEntity(dto);

        book.setCategory(category);
        book.setPublisher(publisher);

        Book newBook = bookRepository.save(book);
        return newBook.getId();

        // Book DTO를 Book 엔티티로 변환하고, category와 publisher를 연결한 후,
        // Book 엔티티를 저장하고 그 ID를 반환하는 것
    }

    @Override
    public List<String> categoryNameList() {
        List<Category> list = categoryRepository.findAll();

        return list.stream().map(entity -> entity.getName()).collect(Collectors.toList());

    }

    @Override
    public BookDto getRow(Long id) {

        Book book = bookRepository.findById(id).get();

        return entityToDto(book);

    }

    @Override
    public Long bookUpdate(BookDto updateDto) {
        Book entity = bookRepository.findById(updateDto.getId()).get();

        entity.setPrice(updateDto.getPrice());
        entity.setSalePrice(updateDto.getSalePrice());

        Book updateBook = bookRepository.save(entity);

        return updateBook.getId();

    }

    @Override
    public void bookDelete(Long id) {
        Book entity = bookRepository.findById(id).get();

        bookRepository.delete(entity);
    }

}
