package com.example.community_board.dto.board;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class EditRequestDto {
    @NotNull(message = "수정할 제목을 입력해주세요")
    private String title;

    @NotNull(message = "수정할 내용을 입력해주세요")
    private String content;
}
