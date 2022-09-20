package com.example.community_board.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
public class TokenReissueDto {

    @NotBlank(message = "토큰을 입력해주세요")
    private String originToken;

    @NotBlank(message = "재발급 토큰을 입력해주세요")
    private String refreshToken;
}
