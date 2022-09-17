package com.example.community_board.dto.comment;

import com.example.community_board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsResponseDto {
    private Long id;
    private String comment;
    private String writer;
    private LocalDate time;

    public CommentsResponseDto toDto(Comment comment){
        return new CommentsResponseDto(comment.getId(), comment.getComment(), comment.getWriter(), comment.getTime());
    }
}
