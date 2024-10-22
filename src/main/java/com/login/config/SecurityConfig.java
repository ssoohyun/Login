package com.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // Spring이 이 클래스를 빈으로 등록 및 설정을 적용
@EnableWebSecurity // Spring Security 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 애플리케이션의 HTTP 보안 설정 정의
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 요청에 대한 보안 설정 시작
                .antMatchers("/test/index.html").authenticated()
                .anyRequest().permitAll() // 나머지 요청은 모두 인증없이 접근 가능
                .and()
                .formLogin()  // 기본 로그인 폼 사용
                .defaultSuccessUrl("/test/index.html", true) // 로그인 성공 시 리다이렉트 경로 설정
                .permitAll() // 모든 사용자에게 로그인 페이지 접근 허용
                .and()
                .logout() // 로그아웃 설정
                .permitAll(); // 모든 사용자에게 로그아웃 페이지 접근 허용
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication() // 메모리 기반 사용자 인증 설정
                .withUser("user").password("{noop}user").roles("USER") // user 계정 설정
                .and()
                .withUser("admin").password("{noop}admin").roles("ADMIN"); // admin 계정 설정
    }
}
