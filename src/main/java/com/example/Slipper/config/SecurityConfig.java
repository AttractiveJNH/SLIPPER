package com.example.Slipper.config;

import org.hibernate.cache.spi.support.CacheUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {


    // 비밀번호 암호화 Bean
    @Bean
    BCryptPasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //권한에 따라 오픈되는 페이지 등록
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/main", "/login", "/join", "/joinProc").permitAll()
                        .requestMatchers("/user_loginProc").hasRole("USER")
                        .requestMatchers("/entre_loginProc").hasRole("ENTRE")
                        .requestMatchers("/mypage/**").hasAnyRole("ADMIN", "ENTREPRE", "USER")
                        .anyRequest().authenticated());


        // 로그인 설정
        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/userloginProc")
                        .loginProcessingUrl("/entreloginProc")
                        .permitAll());

        http
                .csrf((auth)->auth.disable());

        // 개발중이니까 disable

        return http.build();
    }


    // 로그아웃 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));
        return http.build();
    }


}
