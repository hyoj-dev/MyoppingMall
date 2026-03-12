package com.lhj.myoppingmall.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Schema(description = "회원 정보 변경 요청 DTO")
public class MemberUpdateRequestDto {

    @Schema(description = "닉네임", example = "길동이")
    private String nickname;

    @Schema(description = "도시", example = "서울특별시")
    private String city;

    @Schema(description = "도로명 주소", example = "가마로 123 1")
    private String street;

    @Schema(description = "우편번호", example = "123-123")
    private String zipCode;

    public boolean hasAddressChanged() {
        return city != null || street != null || zipCode != null;
    }
}
