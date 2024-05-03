package com.example.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PasswordChangeDto {
    private String email;
    private String currentPassword;
    private String newPassword;
}
