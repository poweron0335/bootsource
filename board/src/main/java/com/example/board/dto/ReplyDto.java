package com.example.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class ReplyDto {

    private Long rno;

    private String text;

    private String writerEmail; // 작성자 아이디(email)

    private String writerName; // 작성자 이름(name)

    private Long bno;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
