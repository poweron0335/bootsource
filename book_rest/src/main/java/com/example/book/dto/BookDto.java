package com.example.book.dto;

import java.time.LocalDateTime;

import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "작가를 입력해주세요")
    private String writer;

    @NotNull(message = "가격을 입력해주세요")
    private Integer price;

    @NotNull(message = "할인가격을 입력해주세요")
    private Integer salePrice;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    // 관계
    @NotBlank(message = "분류를 입력해주세요")
    private String categoryName;

    @NotBlank(message = "출판사를 입력해주세요")
    private String publisherName;

}
