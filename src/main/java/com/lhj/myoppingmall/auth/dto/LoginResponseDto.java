package com.lhj.myoppingmall.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답 DTO")
public record LoginResponseDto(

        @Schema(description = "응답 메시지", example = "로그인에 성공했습니다.")
        String message,

        @Schema(description = "로그인 ID", example = "test1234")
        String loginId,

        @Schema(description = "Access 토큰", example = "a7zx6c23hg7...")
        String accessToken,

        @Schema(description = "Refresh 토큰", example = "xcc87xzc87236...")
        String refreshToken
) {
}
