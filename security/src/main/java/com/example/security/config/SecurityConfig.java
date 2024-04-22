package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // @EnableWebSecurity : 웹에서 security 적용 가능
@Configuration // @Configuration == @Component(@controller, @Service) : 환경 설정용 객체 생성
public class SecurityConfig {

    // 접근제한 개념
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 요청 확인
                .authorizeHttpRequests(autorize -> autorize.requestMatchers("/", "/security/guest", "/auth").permitAll()
                        .requestMatchers("/security/member").hasRole("USER").requestMatchers("/security/admin")
                        .hasRole("ADMIN"))

                // 인증 처리 하도록 제시(웹에서는 대부분 폼 로그인 작업)
                // .formLogin(Customizer.withDefaults()); // default 로그인 페이지 보여주기
                .formLogin(login -> login.loginPage("/member/login").permitAll()) // custom login 페이지 사용
                // .usernameParameter("userid") username 요소 이름 변경 시
                // .passwordParameter("pwd") password 요소 이름 변경
                // .successForwardUrl("null")) 로그인 성공 후 가야할 곳 지정

                // custom logout 페이지 사용
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/") // default 로그인 페이지임
                ); // custom logout

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 임시 - 데이터베이스에 인증을 요청하는 객체
    // InMemoryUserDetailsManager - 메모리에 등록해 놓고 임시로 사용
    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user1")
                .password("{bcrypt}$2a$10$dCHAwcHeO1G6FP2DVxwlyO/kUZlc3t73TeLeiwIVdXO.67QRHxz2e")
                .roles("USER").build();
        UserDetails admin = User.builder()
                .username("admin1")
                .password("{bcrypt}$2a$10$dCHAwcHeO1G6FP2DVxwlyO/kUZlc3t73TeLeiwIVdXO.67QRHxz2e")
                .roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
