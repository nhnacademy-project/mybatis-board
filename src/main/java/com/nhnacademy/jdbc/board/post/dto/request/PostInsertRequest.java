package com.nhnacademy.jdbc.board.post.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PostInsertRequest {

    @Setter
    @NotNull
    private Long userNo;

    @NotNull
    @Size(max = 25)
    private final String title;

    @NotNull
    @Size(max = 250)
    private final String content;
}
