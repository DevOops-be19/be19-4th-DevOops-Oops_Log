package com.devoops.oopslog.member.query.service;

import com.devoops.oopslog.member.query.dto.FindIdDTO;
import com.devoops.oopslog.member.query.dto.ResponseIdDTO;

public interface MemberQueryService {
    void findMemberId(FindIdDTO findIdDTO);
}
