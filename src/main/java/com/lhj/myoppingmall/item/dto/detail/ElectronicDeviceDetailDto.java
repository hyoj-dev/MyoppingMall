package com.lhj.myoppingmall.item.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "전자제품 상품 상세 정보 DTO")
public class ElectronicDeviceDetailDto implements ItemDetailDto{

    @Schema(description = "제조사", example = "Apple")
    private String manufacturerCompany;

    @Schema(description = "보증기간", example = "12")
    private Integer warrantyMonths;

    @Schema(description = "전자제품 상품 소개", example = "Apple의 Iphone14 Pro 입니다.")
    private String description;
}
