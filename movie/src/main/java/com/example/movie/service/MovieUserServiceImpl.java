package com.example.movie.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MovieUserServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 시큐리티에서 사용하는 로그인 메소드
        // User 로 리턴 or User 구현한 CustomUser 로 리턴
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

}