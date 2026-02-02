package com.lhj.myoppingmall.item.dto.update;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClothUpdateDto {
    private Integer size;
    private String brand;
    private String description;
}
