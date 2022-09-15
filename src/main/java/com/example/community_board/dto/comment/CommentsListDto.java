package com.example.community_board.dto.comment;

import com.example.community_board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsListDto {
    private Long id;
    private String comment;
    private String writer;
    private LocalDate time;

    public CommentsListDto(Comment comment){
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.writer = comment.getWriter();
        this.time = comment.getTime();
    }
}
