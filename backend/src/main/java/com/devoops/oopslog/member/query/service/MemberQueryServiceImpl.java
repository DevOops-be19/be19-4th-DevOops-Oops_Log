package com.devoops.oopslog.member.query.service;

import com.devoops.oopslog.member.query.dto.FindIdDTO;
import com.devoops.oopslog.member.query.dto.FindMemberDTO;
import com.devoops.oopslog.member.query.dto.MailDTO;
import com.devoops.oopslog.member.query.dto.ResponseIdDTO;
import com.devoops.oopslog.member.query.mapper.MemberQueryMapper;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberQueryMapper memberQueryMapper;
    private final JavaMailSender mailSender;

    public MemberQueryServiceImpl(MemberQueryMapper memberQueryMapper,
                                  JavaMailSender mailSender) {
        this.memberQueryMapper = memberQueryMapper;
        this.mailSender = mailSender;
    }

    @Override
    public void findMemberId(FindIdDTO findIdDTO) {
        // DB 에서 일치하는 회원 조회
        FindMemberDTO findMemberDTO = memberQueryMapper.findMemberById(findIdDTO);
        if(findMemberDTO == null){
//            throw new RuntimeException("일치하는 회원 정보가 없습니다.");
        }

        // 일치하는 회원이 있다면 메일로 id 발신
        MailDTO mailDTO = new MailDTO();
        mailDTO.setAddress(findMemberDTO.getEmail());
        mailDTO.setSubject("요청 아이디");
        mailDTO.setContent("요청하신 아이디는 - "+findMemberDTO.getMemberId()+" - 입니다.");

        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(mailDTO.getAddress());                 // 받는 사람 이메일
        smm.setSubject(mailDTO.getSubject());            // 이메일 제목
        smm.setText(mailDTO.getContent());               // 이메일 내용
        try {
            mailSender.send(smm);                        // 메일 보내기
            System.out.println("이메일 전송 성공!");
        } catch (MailException e) {
            System.out.println("[-] 이메일 전송중에 오류가 발생하였습니다 " + e.getMessage());
            throw e;
        }

    }
}
