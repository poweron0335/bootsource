package com.example.web1.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MemberDto {

    @Pattern(regexp = "(?=^[A-Za-z])(?=.+\\d)[A-Za-z\\d]{6,12}$", message = "아이디는 영대소문자, 숫자를 사용해서 6~12자리 입니다.")
    @NotEmpty
    private String userid;

    @NotEmpty
    @Pattern(regexp = "(?=^[A-Za-z])(?=.+\\d)(?=.+[!@$%])[A-Za-z\\d!@$%]{8,15}$", message = "비밀번호는 영대소문자, 숫자, 특수문자(!@#$)를 사용해서 8~15자리 입니다.")
    private String password;

    @NotNull(message = "나이는 필수 요건입니다.")
    @Min(value = 0)
    @Max(value = 120)
    private Integer age;

    @NotEmpty(message = "이메일은 필수 요소입니다.")
    @Email
    private String email;

    @Length(min = 2, max = 6, message = "이름은 2~6자리로 작성해 주세요")
    private String name;
}
