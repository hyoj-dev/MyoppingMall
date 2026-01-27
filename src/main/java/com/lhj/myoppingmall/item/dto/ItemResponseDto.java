package com.lhj.myoppingmall.item.dto;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.item.entity.category.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
/*
* Item 공통 응답 Dto
* */
public class ItemResponseDto {
    private Long id;
    private Category category;
    private String name;
    private Long price;
    private String pictureUrl;
    private int stockQuantity;

    public static ItemResponseDto from(Item item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .category(item.getCategory())
                .name(item.getName())
                .price(item.getPrice())
                .pictureUrl(item.getPictureUrl())
                .stockQuantity(item.getStockQuantity())
                .build();
    }
}
