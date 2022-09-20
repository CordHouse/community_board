package com.example.community_board.contoller;

import com.example.community_board.dto.user.TokenReissueDto;
import com.example.community_board.dto.user.UserDelegateRequestDto;
import com.example.community_board.dto.user.UserEmailRequestDto;
import com.example.community_board.dto.user.UserLoginRequestDto;
import com.example.community_board.dto.user.UserPasswordEditRequestDto;
import com.example.community_board.dto.user.UserSignUpRequestDto;
import com.example.community_board.response.Response;
import com.example.community_board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED) // 회원 가입
    public void signup(@RequestBody @Valid UserSignUpRequestDto requestDto) {
        userService.signUp(requestDto);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.CREATED) // 로그인
    public Response login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return Response.success(userService.login(requestDto));
    }

    @PostMapping("/auth/reissue")
    @ResponseStatus(HttpStatus.OK) // 토큰 재발급
    public Response reissue(@RequestBody @Valid TokenReissueDto requestDto) {
        return Response.success(userService.reIssue(requestDto));
    }

    @GetMapping("/userid")
    @ResponseStatus(HttpStatus.OK) // 사용자 아이디 찾기
    public Response findUserId(@RequestBody @Valid UserEmailRequestDto requestDto) {
        return Response.success(userService.findUserId(requestDto));
    }

    @GetMapping("/password")
    @ResponseStatus(HttpStatus.OK) // 임시 비밀번호 발급
    public Response findUserPassword(@RequestBody @Valid UserEmailRequestDto requestDto) {
        return Response.success(userService.findUserPassword(requestDto));
    }

    @PutMapping("/auth/password")
    @ResponseStatus(HttpStatus.OK) // 비밀번호 변경
    public void editUserPassword(@RequestBody @Valid UserPasswordEditRequestDto requestDto) {
        userService.editPassword(requestDto);
    }

    @PutMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public void delegate(@RequestBody @Valid UserDelegateRequestDto requestDto) {
        userService.delegateUser(requestDto);
    }
}
