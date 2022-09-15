package com.example.community_board.dto.comment;

import com.example.community_board.entity.Board;
import com.example.community_board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentsResponseDto {
    private Board board;
    private CreateComment comment;

    public CreateCommentsResponseDto toDto(Comment comment){
        return new CreateCommentsResponseDto(comment.getBoard(), new CreateComment(comment));
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CreateComment{
    private Long id;
    private String comment;
    private String writer;
    private LocalDate time;

    public CreateComment(Comment comment){
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.writer = comment.getWriter();
        this.time = comment.getTime();
    }
}
