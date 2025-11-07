package com.devoops.oopslog.member.command.service;

import com.devoops.oopslog.member.command.dto.SignUpDTO;
import com.devoops.oopslog.member.command.dto.UserDetailInfoDTO;
import com.devoops.oopslog.member.command.dto.UserImpl;
import com.devoops.oopslog.member.command.entity.Member;
import com.devoops.oopslog.member.command.repository.MemberCommandRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        member.setMemberPw(bCryptPasswordEncoder.encode(member.getMemberPw()));

        memberCommandRepository.save(member);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberCommandRepository.findByMemberId(username);

        // 존재하지 않는 아이디
        if (member == null) throw new UsernameNotFoundException(username + " - 회원정보가 존재하지 않음");

        // 정지된 회원
        if (member.getUser_state() == 'S') throw new LockedException(username + " - 정지된 회원");

        // 5회 이상 로그인 시도


        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        // 커스텀한 User 객체 이용
        UserImpl userImpl = new UserImpl(member.getMemberId(), member.getMemberPw(), grantedAuthorities);
        userImpl.setUserInfo(new UserDetailInfoDTO(member.getId()));
        return userImpl;
        // 사용자의 id,pw,권한,하위 정보들을 provider로 전송
//        return new User(member.getMemberId(), member.getMemberPw(), true, true, true, true, grantedAuthorities);
    }
}
