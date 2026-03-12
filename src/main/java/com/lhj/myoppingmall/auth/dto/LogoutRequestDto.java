package com.lhj.myoppingmall.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그아웃 요청 DTO")
public record LogoutRequestDto(

        @Schema(description = "Refresh 토큰", example = "xcc87xzc87236...")
        String refreshToken
) {
}
