package com.lhj.myoppingmall.order.dto.create;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCreateResponseDto {
    private Long orderId;
    private int totalOrderQuantity;
    private Long totalOrderPrice;

    public static OrderCreateResponseDto from(Long orderId, int totalOrderQuantity,Long totalOrderPrice) {
        return OrderCreateResponseDto.builder()
                .orderId(orderId)
                .totalOrderQuantity(totalOrderQuantity)
                .totalOrderPrice(totalOrderPrice)
                .build();
    }
}
