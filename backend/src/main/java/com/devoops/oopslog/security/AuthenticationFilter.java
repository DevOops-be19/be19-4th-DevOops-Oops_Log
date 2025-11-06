package com.devoops.oopslog.security;


import com.devoops.oopslog.member.command.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                Environment env) {
        // authenticationManager를 인지시킴
        super(authenticationManager);
        this.env = env;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDTO credential = new ObjectMapper().readValue(request.getInputStream(),LoginDTO.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(credential.getMember_id(), credential.getMember_pw(), new ArrayList<>()));
        } catch (IOException e) {
            log.info("attemptAuthentication 에서 오류");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 : {}", authResult.toString());
        // 성공한 사용자의 id
        String id = authResult.getName();
        log.info(id);

        // 사용자의 권한 목록
        List<String> roles = authResult.getAuthorities().stream()
//                .map(role->role.getAuthority())
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        log.info("List<String> 형태로 뽑아낸 로그인 한 회원의 권한들 : {}",roles);

        // JWT 토큰 발행
        Claims clasim
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 인증 실패 : {}", failed.getMessage());
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
