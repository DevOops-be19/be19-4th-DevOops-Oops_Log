package com.devoops.oopslog.member.command.service;

import com.devoops.oopslog.member.command.dto.SignUpDTO;

public interface MemberCommandService {
    void signUpMember(SignUpDTO signUpDTO);
}
