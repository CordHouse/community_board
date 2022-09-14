package com.example.community_board.dto.board;

import com.example.community_board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateBoardResponseDto {
    private String title;
    private String content;
    private long viewCount;

    public CreateBoardResponseDto toDto(Board board) {
        return new CreateBoardResponseDto(board.getTitle(), board.getContent(), board.getViewCount());
    }
}
