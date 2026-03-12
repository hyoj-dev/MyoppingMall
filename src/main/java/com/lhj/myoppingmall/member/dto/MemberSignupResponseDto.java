package com.lhj.myoppingmall.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답 DTO")
public record MemberSignupResponseDto(

        @Schema(description = "회원 ID", example = "1")
        Long memberId,

        @Schema(description = "로그인 ID", example = "test1234")
        String loginId
) {
}
