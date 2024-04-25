package com.example.movie.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class MemberDto {

    private Long mid;

    private String email;

    private String password;

    private String nickname;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

}
