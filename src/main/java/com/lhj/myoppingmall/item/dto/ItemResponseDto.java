package com.lhj.myoppingmall.item.dto;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.item.entity.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "상품 공통 응답 DTO")
public class ItemResponseDto {

    @Schema(description = "상품 ID", example = "1")
    private Long itemId;

    @Schema(description = "상품 카테고리", example = "CLOTH")
    private Category category;

    @Schema(description = "상품명", example = "후드티")
    private String name;

    @Schema(description = "상품 가격", example = "35000")
    private Long price;

    @Schema(description = "상품 사진 URL", example = "https://example/item.jpg")
    private String pictureUrl;

    @Schema(description = "남은 상품 개수", example = "5")
    private int stockQuantity;

    public static ItemResponseDto from(Item item) {
        return ItemResponseDto.builder()
                .itemId(item.getId())
                .category(item.getCategory())
                .name(item.getName())
                .price(item.getPrice())
                .pictureUrl(item.getPictureUrl())
                .stockQuantity(item.getStockQuantity())
                .build();
    }
}
