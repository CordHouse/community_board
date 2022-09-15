package com.example.community_board.contoller;

import com.example.community_board.dto.comment.CreateCommentsRequestDto;
import com.example.community_board.dto.comment.EditCommentsRequestDto;
import com.example.community_board.response.Response;
import com.example.community_board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createComments(@PathVariable Long id, @RequestBody @Valid CreateCommentsRequestDto createCommentsRequestDto){
        return Response.success(commentService.createComments(id, createCommentsRequestDto));
    }
    
    // 댓글 수정
    @PutMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response editComments(@PathVariable Long id, @RequestBody @Valid EditCommentsRequestDto editCommentsRequestDto){
        return Response.success(commentService.editComments(id, editCommentsRequestDto));
    }
    
    // 댓글 삭제
    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteComment(@PathVariable Long id){
        return commentService.deleteComment(id);
    }
}
