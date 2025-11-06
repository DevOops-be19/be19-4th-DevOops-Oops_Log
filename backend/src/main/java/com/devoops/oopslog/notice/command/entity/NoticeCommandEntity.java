package com.devoops.oopslog.notice.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notice_board")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NoticeCommandEntity {

//    id, title, content, create_date, modify_date, is_deleted, user_id(FK)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long notice_id;

    @Column(name = "title")
    private String notice_title;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime create_date;

    @Column(name = "is_delete")
    private String is_delete;

    @Column(name = "user_id")
    private Long user_id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member", insertable = false, updatable = false)
    private MemberEntity members;

}
