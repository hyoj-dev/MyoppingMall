package com.lhj.myoppingmall.item.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "의류 상품 상세 정보 DTO")
public class ClothDetailDto implements ItemDetailDto {

    @Schema(description = "사이즈", example = "105")
    private Integer size;

    @Schema(description = "브랜드", example = "NIKE")
    private String brand;

    @Schema(description = "의류 상품 소개", example = "아주 편한 후드티입니다.")
    private String description;
}
