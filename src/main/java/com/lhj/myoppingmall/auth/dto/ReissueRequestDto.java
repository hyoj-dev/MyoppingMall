package com.lhj.myoppingmall.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 재발급 요청 DTO")
public record ReissueRequestDto(

        @Schema(description = "Refresh 토큰", example = "xcc87xzc87236...")
        String refreshToken
) {
}
