package com.nhnacademy.jdbc.board.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Post {

    public static final Character DELETED = 'Y';
    public static final Character NOT_DELETED = 'N';

    private Long postNo;
    private Long userNo;
    private String title;
    private String content;
    private Date createdAt;
    private Character deleteNY;
    private String filePath;
    private Date modifiedAt;
    private Long modifyUserNo;

    public Post(Long userNo, String title, String content, Date createdAt) {
        this.userNo = userNo;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    @Builder
    public Post(Long postNo, String title, String content, Long modifyUserNo) {
        this.postNo = postNo;
        this.title = title;
        this.content = content;
        this.modifyUserNo = modifyUserNo;
    }
}
