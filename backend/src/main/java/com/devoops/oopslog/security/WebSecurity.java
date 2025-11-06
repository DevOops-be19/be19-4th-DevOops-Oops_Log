package com.devoops.oopslog.security;

import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
public class WebSecurity {
    private Environment env;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    public WebSecurity( Environment env,
                        JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.env = env;     // JWT Token의 payload에 만료시간 만들다가 추가
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        /* 설명. Spring Security 모듈 추가 후 default 로그인 페이지 제거 및 인가 설정 */
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(authz ->
                        authz.requestMatchers("/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET,"/health").permitAll()
//                                        .requestMatchers(HttpMethod.POST,"/users/**").permitAll()
//                                        .requestMatchers(HttpMethod.GET,"/test").permitAll()
//                                        .requestMatchers("/actuator/**").permitAll()
//                     .requestMatchers(HttpMethod.GET,"/users/**").hasRole("ENTERPRISE")
//                                        .requestMatchers(HttpMethod.GET,"/test1/**","/test2/**").hasAnyRole("ADMIN","ENTERPRISE")
                                        .anyRequest()
                                        .authenticated()
                )
                /* 설명. Session 방식이 아닌 JWT Token 방식으로 인증된 회원(Authentication)을 Local Thread로 저장하겠다. */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                // authenticationFilter를 추가하는 과정
                http.addFilter(getAuthenticationFilter(authenticationManager()));

        return http.build();
    }

    private Filter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager,env);
    }

}
