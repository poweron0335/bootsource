package com.example.club.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ClubUserDetailService implements UserDetailsService {

    // UserDetails <-------- User <------- CustomUser
    //

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인 담당 메소드
        // username : 회원 아이디 개념
        log.info("로그인 요청 {} ", username);
        return null;
    }

}
