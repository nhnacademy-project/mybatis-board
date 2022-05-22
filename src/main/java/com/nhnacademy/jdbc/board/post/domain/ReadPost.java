package com.nhnacademy.jdbc.board.post.domain;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadPost {

    private Long postNo;
    private Long writerNo;
    private String username;
    private String writer;
    private String title;
    private String content;
    private Date createdAt;
    private Character deleteNY;
    private String filePath;
    private Long modifyUserNo;
    private Date modifiedAt;
    private Long viewCount;
    private Integer commentCount;
}
