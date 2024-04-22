package com.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {

    // SecurityConfig 의 passwordEncoder() 가 실행되면서 주입됨
    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화(encode), 원 비밀번호와 암호화된 비밀번호의 일치 여부(matches)

    @Test
    public void testEncoder() {
        String password = "1111"; // 원 비밀번호

        // 원 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(password);

        // password : 1111, encode password :
        // {bcrypt}$2a$10$dCHAwcHeO1G6FP2DVxwlyO/kUZlc3t73TeLeiwIVdXO.67QRHxz2e
        System.out.println("password : " + password + ", encode password : " + encodePassword);

        // matches(원비밀번호,암호화된비밀번호) = true
        System.out.println(passwordEncoder.matches(password, encodePassword));
    }
}
