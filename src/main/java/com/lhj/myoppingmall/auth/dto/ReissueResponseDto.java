package com.lhj.myoppingmall.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 재발급 응답 DTO")
public record ReissueResponseDto(

        @Schema(description = "응답 메시지", example = "재발급 성공.")
        String message,

        @Schema(description = "Access 토큰", example = "a7zx6c23hg7...")
        String accessToken,

        @Schema(description = "Refresh 토큰", example = "xcc87xzc87236...")
        String refreshToken
) {
}
