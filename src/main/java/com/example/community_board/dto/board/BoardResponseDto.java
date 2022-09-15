package com.example.community_board.dto.board;

import com.example.community_board.dto.comment.CommentsListDto;
import com.example.community_board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
    private String title;
    private String content;
    private long viewCount;
    private List<CommentsListDto> commentList;

    public BoardResponseDto toDto(Board board, List<CommentsListDto> commentList) {
        return new BoardResponseDto(board.getTitle(), board.getContent(), board.getViewCount(), commentList);
    }
}
