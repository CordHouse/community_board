package com.example.community_board.advice;

import com.example.community_board.exception.board.BoardNotFoundException;
import com.example.community_board.exception.board.ListNotFoundException;
import com.example.community_board.exception.user.NotValidTokenException;
import com.example.community_board.exception.user.UserIdCollisionException;
import com.example.community_board.exception.user.UserNotFoundException;
import com.example.community_board.exception.user.UserWrongPasswordException;
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
        return Response.failure(400, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response boardNotFoundException() {
        return Response.failure(404, "게시글을 찾을 수 없습니다.");
    }

    @ExceptionHandler(ListNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response listNotFoundException() {
        return Response.failure(404, "조회하고자하는 리스트가 비어있습니다.");
    }

    @ExceptionHandler(UserIdCollisionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response userIdCollisionException() {
        return Response.failure(400, "해당 회원은 이미 존재하는 회원입니다.");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response userNotFoundException() {
        return Response.failure(404, "해당 회원을 찾을 수 없습니다.");
    }

    @ExceptionHandler(UserWrongPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response userWrongPasswordException() {
        return Response.failure(400, "비밀번호를 잘못 입력하셨습니다.");
    }

    @ExceptionHandler(NotValidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response notValidTokenException() {
        return Response.failure(400, "유효하지 않은 토큰입니다.");
    }
}
