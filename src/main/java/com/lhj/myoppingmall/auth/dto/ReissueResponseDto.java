package com.lhj.myoppingmall.auth.dto;

public record ReissueResponseDto(
        String message,
        String accessToken,
        String refreshToken
) {
}
