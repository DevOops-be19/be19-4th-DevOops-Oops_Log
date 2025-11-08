package com.devoops.oopslog.member.query.controller;

import com.devoops.oopslog.member.query.dto.FindIdDTO;
import com.devoops.oopslog.member.query.dto.ResponseIdDTO;
import com.devoops.oopslog.member.query.service.MemberQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberQueryController {
    private final MemberQueryService memberQueryService;

    public MemberQueryController(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @PostMapping("/find-id")
    public ResponseEntity<?> findMemberId(@RequestBody FindIdDTO findIdDTO){
        memberQueryService.findMemberId(findIdDTO);
        return ResponseEntity.ok().build();
    }
}
