package com.lhj.myoppingmall.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "주문 상품 DTO")
public class OrderItemDto {

    @Schema(description = "상품 ID", example = "1")
    private Long itemId;

    @Schema(description = "주문 수량", example = "3")
    private Integer orderQuantity;

    public static OrderItemDto from(Long itemId, Integer orderQuantity) {
        return OrderItemDto.builder()
                .itemId(itemId)
                .orderQuantity(orderQuantity)
                .build();
    }
}
