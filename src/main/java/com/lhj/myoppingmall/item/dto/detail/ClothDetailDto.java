package com.lhj.myoppingmall.item.dto.detail;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClothDetailDto implements ItemDetailDto {
    private Integer size;
    private String brand;
    private String description;
}
