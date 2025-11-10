// SecurityConfig.java
package com.devoops.oopslog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // REST API면 비활성
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() //  모든 경로 허용
                )
                .httpBasic(Customizer.withDefaults()); // 기본 인증 비활성화(테스트용)
        return http.build();
    }
}

