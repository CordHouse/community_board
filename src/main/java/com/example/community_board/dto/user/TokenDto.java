package com.example.community_board.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenDto {

    private String originToken;
    private String refreshToken;
    private String grantedType;
    private Long validationTime;
}
