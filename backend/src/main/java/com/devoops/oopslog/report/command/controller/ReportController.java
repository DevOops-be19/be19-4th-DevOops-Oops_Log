package com.devoops.oopslog.report.command.controller;

import com.devoops.oopslog.report.command.dto.ReportRequestDTO;
import com.devoops.oopslog.report.command.entity.ReportEntity;
import com.devoops.oopslog.report.command.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody ReportRequestDTO dto){
        ReportEntity saved = reportService.createReport(dto);
        return ResponseEntity.ok().body("신고가 등록되었습니다");
    }
}
