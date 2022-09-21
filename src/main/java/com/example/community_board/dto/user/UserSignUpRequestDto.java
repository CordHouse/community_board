package com.example.community_board.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class UserSignUpRequestDto {
    @NotNull(message = "사용자 이름을 입력해주세요")
    String username;

    @NotNull(message = "사용자 아이디를 입력해주세요")
    String userId;

    @NotNull(message = "사용자 비밀번호를 입력해주세요")
    String password;

    @NotNull(message = "사용자 이메일을 입력해주세요")
    String email;
}
