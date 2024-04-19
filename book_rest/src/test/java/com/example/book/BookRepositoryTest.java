package com.example.book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.book.dto.BookDto;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;
import com.example.book.repository.BookRepository;
import com.example.book.repository.CategoryRepository;
import com.example.book.repository.PublisherRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void insertTest() {
        // 카테고리 : 컴퓨터 경제/경영 인문 소설 자기계발
        // 출판사 : 로드북 다산 웅진지식하우스 비룡소 을유문화사

        Category category = Category.builder().name("컴퓨터").build();
        categoryRepository.save(category);
        category = Category.builder().name("경제/경영").build();
        categoryRepository.save(category);
        category = Category.builder().name("인문").build();
        categoryRepository.save(category);
        category = Category.builder().name("소설").build();
        categoryRepository.save(category);
        category = Category.builder().name("자기계발").build();
        categoryRepository.save(category);

        Publisher publisher = Publisher.builder().name("로드북").build();
        publisherRepository.save(publisher);
        publisher = Publisher.builder().name("다산").build();
        publisherRepository.save(publisher);
        publisher = Publisher.builder().name("웅진지식하우스").build();
        publisherRepository.save(publisher);
        publisher = Publisher.builder().name("비룡소").build();
        publisherRepository.save(publisher);
        publisher = Publisher.builder().name("을유문화사").build();
        publisherRepository.save(publisher);

    }

    @Test
    public void insertBookTest() {

        LongStream.rangeClosed(1, 20).forEach(i -> {
            Book book = Book.builder()
                    .title("스프링 부트 프레임워크" + i)
                    .writer("홍길동")
                    .price(35000)
                    .salePrice(20000)
                    .category(Category.builder().id((i % 5) + 1).build())
                    .publisher(Publisher.builder().id((i % 5) + 1).build())
                    .build();

            bookRepository.save(book);
        });
    }

    @Transactional
    @Test
    public void bookList() {
        List<Book> books = bookRepository.findAll();

        books.forEach(book -> {
            System.out.println(book);
            System.out.println("출판사 " + book.getPublisher().getName());
            System.out.println("분야 " + book.getCategory().getName());

        });
    }

    @Test
    public void testCateNameList() {
        List<Category> list = categoryRepository.findAll();

        list.forEach(category -> System.out.println(category));
        // Category(id=1, name=컴퓨터)
        // List<String> cateList = new ArrayList<>();
        // list.forEach(category -> cateList.add(category.getName()));

        List<String> cateList = list.stream().map(entity -> entity.getName()).collect(Collectors.toList());

        cateList.forEach(System.out::println);

    }

    @Test
    public void testSearchList() {

        // Spring Data JPA 페이징 처리 객체
        // page 번호 : 0 부터 시작
        // Pageable pageable = PageRequest.of(0, 10);
        // Pageable pageable = PageRequest.of(0, 10, Direction.DESC);
        // Pageable pageable = PageRequest.of(0, 10, Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        // Page : 페이지 나누기에 필요한 메소드 제공
        // == PageDto와 같은 역할
        Page<Book> result = bookRepository.findAll(bookRepository.makePredicate("t", "스프링"), pageable);

        System.out.println("전체 행 수 " + result.getTotalElements());
        System.out.println("필요한 페이지 수 " + result.getTotalPages());
        result.getContent().forEach(book -> System.out.println(book));
    }

    // @Test
    // public void testPubliNameList() {
    // List<Publisher> list = publisherRepository.findAll();

    // list.forEach(publisher);
    // }

}
