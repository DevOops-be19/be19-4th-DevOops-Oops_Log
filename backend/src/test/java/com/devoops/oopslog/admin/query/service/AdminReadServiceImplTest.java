package com.devoops.oopslog.admin.query.service;

import com.devoops.oopslog.admin.query.dto.AllMemberDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback
class AdminReadServiceImplTest {

    @Autowired
    private AdminReadService adminReadService;

    @DisplayName("관리자 회원 조회 테스트")
    @Test
    public void testSelectMember() {
        Assertions.assertDoesNotThrow(() -> {
            List<AllMemberDTO> list =
                    adminReadService.getAllMember(
                      1,5
                    );
            list.forEach(System.out::println);
        });
    }
}