package com.example.community_board.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserPasswordEditRequestDto {

    @NotBlank(message = "현재 비밀번호를 입력해주세요")
    private String originPassword;

    @NotBlank(message = "변경할 비밀번호를 입력해주세요")
    private String refreshPassword;
}