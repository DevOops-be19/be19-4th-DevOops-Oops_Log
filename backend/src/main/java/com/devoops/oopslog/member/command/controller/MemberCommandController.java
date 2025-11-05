package com.devoops.oopslog.member.command.controller;

import com.devoops.oopslog.member.command.dto.LoginDTO;
import com.devoops.oopslog.member.command.dto.SignUpDTO;
import com.devoops.oopslog.member.command.service.MemberCommandService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberCommandController {
    private final MemberCommandService memberCommandService;

    public MemberCommandController(MemberCommandService memberCommandService) {
        this.memberCommandService = memberCommandService;
    }

    @GetMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return null;
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestBody SignUpDTO signUpDTO) {
        memberCommandService.signUpMember(signUpDTO);
        return null;
    }
}
