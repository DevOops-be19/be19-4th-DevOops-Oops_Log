package com.devoops.oopslog.member.query.service;

import com.devoops.oopslog.member.query.dto.FindIdDTO;
import com.devoops.oopslog.member.query.dto.ResponseIdDTO;
import com.devoops.oopslog.member.query.mapper.MemberQueryMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberQueryMapper memberQueryMapper;

    public MemberQueryServiceImpl(MemberQueryMapper memberQueryMapper) {
        this.memberQueryMapper = memberQueryMapper;
    }

    @Override
    public ResponseIdDTO findMemberId(FindIdDTO findIdDTO) {
        String foundedId = memberQueryMapper.findMemberIdById(findIdDTO);
        if(foundedId == null){
//            throw new RuntimeException("일치하는 회원 정보가 없습니다.");
        }
        return new ResponseIdDTO(foundedId);
    }
}
