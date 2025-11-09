package com.devoops.oopslog.report.command.service;

import com.devoops.oopslog.report.command.dto.ReportRequestDTO;
import com.devoops.oopslog.report.command.entity.*;
import com.devoops.oopslog.report.command.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class ReportSerivceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final ReportCategoryRepository reportCategoryRepository;
    private final OohRecordRepository oohRecordRepository;
    private final OopsRecordRepository oopsRecordRepository;
    private final CommentsRepository commentsRepository;

    @Autowired
    public ReportSerivceImpl(ReportRepository reportRepository,
                             MemberRepository memberRepository,
                             ReportCategoryRepository reportCategoryRepository,
                             OohRecordRepository oohRecordRepository,
                             OopsRecordRepository oopsRecordRepository,
                             CommentsRepository commentsRepository) {
        this.reportRepository = reportRepository;
        this.memberRepository = memberRepository;
        this.reportCategoryRepository = reportCategoryRepository;
        this.oohRecordRepository = oohRecordRepository;
        this.oopsRecordRepository = oopsRecordRepository;
        this.commentsRepository = commentsRepository;
    }


    @Override
    public ReportEntity createReport(ReportRequestDTO dto) {
        // 필수 엔티티 조회
        MemberEntity user = memberRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        ReportCategoryEntity category = reportCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("신고 카테고리가 존재하지 않습니다."));

        // 빌더 패턴으로 ReportEntity 생성
        ReportEntity report = ReportEntity.builder()
                .reportDate(new Date())  // 현재 시각
                .state("U")              // 기본 상태: U (Unprocessed)
                .categoryId(category)
                .userId(user)
                .build();

        // 선택적 신고 대상 설정 (null 가능)
        if (dto.getOohId() != null) {
            OohRecordEntity ooh = oohRecordRepository.findById(dto.getOohId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 Ooh 게시글이 존재하지 않습니다."));
            if (reportRepository.existsByOohId(ooh)) {
                throw new IllegalStateException("이미 신고된 게시글입니다.");
            }
            report.setOohId(ooh);
        }

        if (dto.getOopsId() != null) {
            OopsRecordEntity oops = oopsRecordRepository.findById(dto.getOopsId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 Oops 게시글이 존재하지 않습니다."));
            if (reportRepository.existsByOopsId(oops)) {
                throw new IllegalStateException("이미 신고된 게시글입니다.");
            }
            report.setOopsId(oops);
        }

        if (dto.getCommentId() != null) {
            CommentsEntity comment = commentsRepository.findById(dto.getCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
            if (reportRepository.existsByCommentId(comment)) {
                throw new IllegalStateException("이미 신고된 댓글입니다.");
            }
            report.setCommentId(comment);
        }

        return reportRepository.save(report);
    }
}
