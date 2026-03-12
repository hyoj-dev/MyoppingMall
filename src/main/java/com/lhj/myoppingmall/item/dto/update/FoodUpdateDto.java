package com.lhj.myoppingmall.item.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@Schema(description = "음식 상품 수정 DTO")
public class FoodUpdateDto {

    @Schema(description = "음식 상품명", example = "사과 1kg")
    private String name;

    @Schema(description = "음식 상품 가격", example = "15000")
    private Long price;

    @Schema(description = "음식 상품 사진 URL", example = "https://example/food.jpg")
    private String pictureUrl;

    @Schema(description = "음식 상품 남은 수량", example = "3")
    private Integer stockQuantity;

    @Schema(description = "음식 상품 소개", example = "아주 달달한 사과(1kg)입니다.")
    private String description;


    @Schema(description = "제조 업체", example = "마음 농원")
    private String manufacturerCompany;

    @Schema(description = "유통기한", example = "2026-06-01")
    private LocalDate expireDate;
}
