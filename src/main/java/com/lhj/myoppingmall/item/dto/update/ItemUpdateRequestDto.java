package com.lhj.myoppingmall.item.dto.update;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemUpdateRequestDto {
    private String name;
    private Long price;
    private String pictureUrl;
    private Integer stockQuantity;

    private ClothUpdateDto cloth;
    private FoodUpdateDto food;
    private ElectronicDeviceUpdateDto ed;
}
