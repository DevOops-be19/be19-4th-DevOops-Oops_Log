package com.devoops.oopslog.member.command.service;

import com.devoops.oopslog.member.command.dto.SignUpDTO;
import com.devoops.oopslog.member.command.entity.Member;
import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class MemberCommandServiceImpl implements MemberCommandService {
    private final MemberCommandRepository memberCommandRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    public MemberCommandServiceImpl(MemberCommandRepository memberCommandRepository,
                                    BCryptPasswordEncoder bCryptPasswordEncoder,
                                    ModelMapper modelMapper) {
        this.memberCommandRepository = memberCommandRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void signUpMember(SignUpDTO signUpDTO) {
        LocalDateTime now = LocalDateTime.now();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // 기본값 세팅
        Member member = modelMapper.map(signUpDTO, Member.class);
        member.setUser_state('A');
        member.setSign_up_date(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        member.setMember_pw(bCryptPasswordEncoder.encode(member.getMember_pw()));

        memberCommandRepository.save(member);
    }
}
