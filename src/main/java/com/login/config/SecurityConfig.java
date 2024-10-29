package com.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Spring이 이 클래스를 빈으로 등록 및 설정을 적용
@EnableWebSecurity // Spring Security 활성화
public class SecurityConfig {

    // SecurityFilterChain을 사용한 HTTP 보안 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/test/index.html").authenticated() // 인증 필요한 경로 설정
                .anyRequest().permitAll() // 나머지 요청은 모두 인증 없이 접근 가능
                .and()
                .formLogin()
                .defaultSuccessUrl("/test/index.html", true) // 로그인 성공 시 리다이렉트 경로 설정
                .permitAll() // 로그인 페이지는 모두 접근 가능
                .and()
                .logout()
                .permitAll(); // 로그아웃도 모두 접근 가능

        return http.build();
    }

    // In-Memory 기반 사용자 인증 설정
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password("user") // 인코딩 지정 안하는 경우엔 {noop}user로 설정 (DelegatingPasswordEncoder의 NoOpPasswordEncoder)
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password("admin") // 인코딩 지정 안하는 경우엔 {noop}admin으로 설정 (DelegatingPasswordEncoder의 NoOpPasswordEncoder)
                .roles("ADMIN")
                .build());
        return manager;
    }

    // 비밀번호 인코딩 설정 (평문 사용)
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
