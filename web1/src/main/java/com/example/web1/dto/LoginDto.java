package com.example.web1.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data

public class LoginDto {

    @Email(message = "이메일을 확인해주세요")
    @NotEmpty // @NotNull + "" 값 불가 (비어 있을 수 없습니다)
    private String email;

    @Length(min = 2, max = 6) // 길이가 2에서 6 사이여야 합니다
    // @NotBlank // @NotEmpty + "" 값 불가
    private String name;

}
