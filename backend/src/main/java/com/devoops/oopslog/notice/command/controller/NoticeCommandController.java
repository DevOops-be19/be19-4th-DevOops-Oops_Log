package com.devoops.oopslog.notice.command.controller;

import com.devoops.oopslog.notice.command.service.NoticeCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
public class NoticeCommandController {
    private final NoticeCommandService noticeCommandService;

    @Autowired
    public NoticeCommandController(NoticeCommandService noticeCommandService) {
        this.noticeCommandService = noticeCommandService;
    }

    @PostMapping
}
