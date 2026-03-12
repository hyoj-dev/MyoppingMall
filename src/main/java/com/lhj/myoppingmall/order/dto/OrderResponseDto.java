package com.lhj.myoppingmall.order.dto;

import com.lhj.myoppingmall.order.entity.Order;
import com.lhj.myoppingmall.order.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "주문 DTO")
public class OrderResponseDto {

    @Schema(description = "주문 상품 ID", example = "1")
    private Long orderId;

    @Schema(description = "주문 날짜", example = "2026-03-12-15:30")
    private LocalDateTime orderedAt;

    @Schema(description = "주문 상태", example = "ORDERED")
    private OrderStatus orderStatus;

    @Schema(description = "총 주문 수량", example = "5")
    private int totalOrderQuantity;

    @Schema(description = "총 주문 가격", example = "125000")
    private Long totalOrderPrice;

    @Schema(description = "대표 상품명", example = "후드티")
    private String summaryItemName;

    @Schema(description = "대표 상품 외 개수", example = "1")
    private int orderItemsQuantity;

    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .orderedAt(order.getOrderedAt())
                .orderStatus(order.getOrderStatus())
                .totalOrderPrice(order.getTotalPrice())
                .totalOrderQuantity(order.getTotalQuantity())
                .summaryItemName(order.getOrderItems().get(0).getItem().getName())
                .orderItemsQuantity(order.getOrderItems().size() - 1)
                .build();
    }
}
