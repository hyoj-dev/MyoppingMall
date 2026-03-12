package com.lhj.myoppingmall.item.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "전자제품 상품 수정 DTO")
public class ElectronicDeviceUpdateDto {

    @Schema(description = "전자제품 상품명", example = "아이폰 14 pro")
    private String name;

    @Schema(description = "전자제품 상품 가격", example = "1000000")
    private Long price;

    @Schema(description = "전자제품 상품 사진 URL", example = "https://example/electronic-device.jpg")
    private String pictureUrl;

    @Schema(description = "전자제품 상품 남은 수량", example = "3")
    private Integer stockQuantity;

    @Schema(description = "전자제품 상품 소개", example = "Apple의 Iphone14 Pro 입니다.")
    private String description;


    @Schema(description = "제조사", example = "Apple")
    private String manufacturerCompany;

    @Schema(description = "보증기간", example = "12")
    private Integer warrantyMonths;
}