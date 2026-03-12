package com.lhj.myoppingmall.order.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "주문 생성 응답 DTO")
public class OrderCreateResponseDto {

    @Schema(description = "주문 ID", example = "1")
    private Long orderId;

    @Schema(description = "총 주문 수량", example = "5")
    private int totalOrderQuantity;

    @Schema(description = "총 주문 금액", example = "95000")
    private Long totalOrderPrice;

    public static OrderCreateResponseDto from(Long orderId, int totalOrderQuantity,Long totalOrderPrice) {
        return OrderCreateResponseDto.builder()
                .orderId(orderId)
                .totalOrderQuantity(totalOrderQuantity)
                .totalOrderPrice(totalOrderPrice)
                .build();
    }
}
