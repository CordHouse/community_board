package com.example.community_board.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentsRequestDto {
    @NotBlank(message = "댓글을 작성해주세요")
    private String comment;
}
