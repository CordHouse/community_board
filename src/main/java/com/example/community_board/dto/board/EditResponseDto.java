package com.example.community_board.dto.board;

import com.example.community_board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditResponseDto {
    private String title;
    private String content;
    private long viewCount;

    public EditResponseDto toDto(Board board) {
        return new EditResponseDto(board.getTitle(), board.getContent(), board.getViewCount());
    }
}
