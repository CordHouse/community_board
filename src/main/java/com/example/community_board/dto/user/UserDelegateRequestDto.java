package com.example.community_board.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDelegateRequestDto {

    @NotBlank(message = "권한을 부여할 사용자의 ID를 입력해주세요")
    private String userId;
}
