package com.lhj.myoppingmall.item.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "의류 상품 수정 DTO")
public class ClothUpdateDto {

    @Schema(description = "의류 상품명", example = "후드티")
    private String name;

    @Schema(description = "의류 상품 가격", example = "35000")
    private Long price;

    @Schema(description = "의류 상품 사진 URL", example = "https://example/cloth.jpg")
    private String pictureUrl;

    @Schema(description = "의류 상품 남은 수량", example = "5")
    private Integer stockQuantity;

    @Schema(description = "의류 상품 소개", example = "아주 편한 후드티입니다.")
    private String description;


    @Schema(description = "사이즈", example = "105")
    private Integer size;

    @Schema(description = "브랜드", example = "NIKE")
    private String brand;
}
