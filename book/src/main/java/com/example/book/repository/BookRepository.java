package com.example.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

public interface BookRepository extends JpaRepository<Book, Long> {

}
