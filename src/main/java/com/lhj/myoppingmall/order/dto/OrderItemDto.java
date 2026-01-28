package com.lhj.myoppingmall.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor
@Builder
public class OrderItemDto {
    private Long itemId;
    private Integer orderQuantity;

    public static OrderItemDto from(Long itemId, Integer orderQuantity) {
        return OrderItemDto.builder()
                .itemId(itemId)
                .orderQuantity(orderQuantity)
                .build();
    }
}
