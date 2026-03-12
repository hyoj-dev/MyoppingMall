package com.lhj.myoppingmall.item.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@Schema(description = "음식 상품 상세 정보 DTO")
public class FoodDetailDto implements ItemDetailDto{

    @Schema(description = "제조 업체", example = "마음 농원")
    private String manufacturerCompany;

    @Schema(description = "유통기한", example = "2026-06-01")
    private LocalDate expireDate;

    @Schema(description = "음식 상품 소개", example = "아주 달달한 사과(1kg)입니다.")
    private String description;
}
