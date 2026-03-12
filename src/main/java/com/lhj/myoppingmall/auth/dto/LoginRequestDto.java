package com.lhj.myoppingmall.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청 DTO")
public record LoginRequestDto(

        @Schema(description = "로그인 ID", example = "test1234")
        String loginId,

        @Schema(description = "비밀번호", example = "password1234")
        String password
) {
}
