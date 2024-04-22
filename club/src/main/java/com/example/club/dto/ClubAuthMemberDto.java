package com.example.club.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ClubAuthMemberDto extends User {

    // db에서 인증된 정보를 담을 객체

    private String email;
    private String name;
    private boolean fromSocial;

    // ClubAuthMemberDto 클래스의 생성자를 정의한다.
    public ClubAuthMemberDto(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        // User 클래스의 생성자를 호출하여 username, password, authorities를 전달
        super(username, password, authorities);

        // username을 이용하여 email 필드를 초기화합니다.
        this.email = username;

        // fromSocial을 이용하여 fromSocial 필드를 초기화합니다.
        this.fromSocial = fromSocial;

    }

}
