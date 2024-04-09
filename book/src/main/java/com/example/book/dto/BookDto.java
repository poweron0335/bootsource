package com.example.book.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class BookDto {

    private Long id;

    private String title;

    private String writer;

    private int price;

    private int sale_price;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
