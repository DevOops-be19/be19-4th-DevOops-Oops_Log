package com.devoops.oopslog.security;


import com.devoops.oopslog.member.command.dto.LoginDTO;
import com.devoops.oopslog.member.command.dto.UserImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
        Claims claims = Jwts.claims().setSubject(id);
        claims.put("auth",roles);

        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new java.util.Date(System.currentTimeMillis() + Long.parseLong(env.getProperty(("token.expiration_time")))))
                .signWith(SignatureAlgorithm.HS512,env.getProperty("token.secret"))
                .compact();

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("token : {}",token);
        response.addHeader("token", token);

        // 성공 객체 반환
        UserImpl user = (UserImpl) authResult.getPrincipal();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{");
        response.getWriter().write("\"success\": \"로그인 성공\",");
        response.getWriter().write("\"id\": \""+user.getId()+"\"");
        response.getWriter().write("}");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 인증 실패 : {}", failed.getMessage());
        // 인증 실패 응답
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\": \"" + failed.getMessage() + "\"}");
//        super.unsuccessfulAuthentication(request, response, failed);
    }
}
