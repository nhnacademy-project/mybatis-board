package com.nhnacademy.jdbc.board.post.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Page<T> {

    private final List<T> pageList;
    private final Integer page;
    private final Integer start;
    private final Integer end;
    private final Integer totalPage;

    @Builder
    public Page(List<T> pageList, Integer page, Integer start, Integer end, Integer totalPage) {
        this.pageList = pageList;
        this.page = page;
        this.start = start;
        this.end = end;
        this.totalPage = totalPage;
    }
}
