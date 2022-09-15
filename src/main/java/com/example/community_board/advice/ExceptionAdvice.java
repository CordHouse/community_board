package com.example.community_board.advice;

import com.example.community_board.exception.board.BoardNotFoundException;
import com.example.community_board.exception.board.ListNotFoundException;
import com.example.community_board.exception.comment.NotFoundcommentsException;
import com.example.community_board.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Response.failure(404, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response boardNotFoundException() {
        return Response.failure(403, "해당 id값에 해당하는 게시글을 찾을 수 없습니다.");
    }

    @ExceptionHandler(ListNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response listNotFoundException() {
        return Response.failure(403, "조회하고자하는 리스트가 비어있습니다.");
    }

    @ExceptionHandler(NotFoundcommentsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundcommentsException() {
        return Response.failure(400, "해당 게시물의 댓글이 존재하지 않습니다.");
    }
}
