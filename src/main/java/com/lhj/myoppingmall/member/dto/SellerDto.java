package com.lhj.myoppingmall.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "판매자 DTO")
public class SellerDto {

    @Schema(description = "판매자 ID", example = "1")
    private Long memberId;

    @Schema(description = "판매자 닉네임", example = "길동이")
    private String nickname;
}
