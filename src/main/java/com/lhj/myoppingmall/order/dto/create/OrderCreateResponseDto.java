package com.lhj.myoppingmall.order.dto.create;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCreateResponseDto {
    private Long orderId;
    private Long totalPrice;

    public static OrderCreateResponseDto from(Long orderId, Long totalPrice) {
        return OrderCreateResponseDto.builder()
                .orderId(orderId)
                .totalPrice(totalPrice)
                .build();
    }
}
