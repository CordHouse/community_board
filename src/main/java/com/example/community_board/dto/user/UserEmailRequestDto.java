package com.example.community_board.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEmailRequestDto {

    @NotBlank(message="이메일을 입력해주세요")
    private String email;
}
