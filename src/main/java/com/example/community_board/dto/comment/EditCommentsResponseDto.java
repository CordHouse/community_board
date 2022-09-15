package com.example.community_board.dto.comment;

import com.example.community_board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCommentsResponseDto {
    private Long id;
    private String comment;
    private String writer;
    private LocalDate time;

    public EditCommentsResponseDto toDto(Comment comment){
        return new EditCommentsResponseDto(comment.getId(), comment.getComment(), comment.getWriter(), comment.getTime());
    }
}
