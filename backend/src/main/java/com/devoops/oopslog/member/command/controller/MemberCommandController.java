package com.devoops.oopslog.member.command.controller;

import com.devoops.oopslog.member.command.dto.LoginDTO;
import com.devoops.oopslog.member.command.dto.SignUpDTO;
import com.devoops.oopslog.member.command.service.MemberCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberCommandController {
    private final MemberCommandService memberCommandService;

    public MemberCommandController(MemberCommandService memberCommandService) {
        this.memberCommandService = memberCommandService;
    }

    @GetMapping("/health")
    public String health() {
        return "I'm OK.";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok().body("로그인 성공");
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody SignUpDTO signUpDTO) {
        memberCommandService.signUpMember(signUpDTO);
        return null;
    }
}
