package com.lhj.myoppingmall.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SellerDto {
    private Long memberId;
    private String nickname;
}
