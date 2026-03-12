package com.lhj.myoppingmall.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 요청 DTO")
public record MemberSignupRequestDto(

        @Schema(description = "로그인 ID", example = "test1234")
        String loginId,

        @Schema(description = "비밀번호", example = "password1234")
        String password,

        @Schema(description = "회원 이름", example = "홍길동")
        String name,

        @Schema(description = "닉네임", example = "길동이")
        String nickname,


        @Schema(description = "도시", example = "서울특별시")
        String city,

        @Schema(description = "도로명 주소", example = "가마로 123 1")
        String street,

        @Schema(description = "우편번호", example = "123-123")
        String zipCode
) {
}
