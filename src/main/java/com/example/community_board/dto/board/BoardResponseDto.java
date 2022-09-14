package com.example.community_board.dto.board;

import com.example.community_board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
    private String title;
    private String content;
    private long viewCount;

    public BoardResponseDto toDto(Board board) {
        return new BoardResponseDto(board.getTitle(), board.getContent(), board.getViewCount());
    }
}
