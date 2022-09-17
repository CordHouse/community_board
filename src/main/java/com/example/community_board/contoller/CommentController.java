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
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComments(@PathVariable Long id, @RequestBody @Valid CreateCommentsRequestDto createCommentsRequestDto){
        commentService.createComments(id, createCommentsRequestDto);
    }

    // 게시글 별 댓글 조회
    @GetMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getComments(@PathVariable Long id){
        return Response.success(commentService.getComments(id));
    }
    
    // 댓글 수정
    @PutMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editComments(@PathVariable Long id, @RequestBody @Valid EditCommentsRequestDto editCommentsRequestDto){
        commentService.editComments(id, editCommentsRequestDto);
    }
    
    // 댓글 삭제
    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
    }
}
