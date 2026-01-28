package com.lhj.myoppingmall.order.dto;

import com.lhj.myoppingmall.order.entity.Order;
import com.lhj.myoppingmall.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderedAt;
    private OrderStatus orderStatus;
    private Long totalPrice;
    private int totalOrderedQuantity;
    private String summaryItemName; //대표 상품명
    private int orderItemsQuantity; //대표 상품 외 개수

    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .orderedAt(order.getOrderAt())
                .orderStatus(order.getOrderStatus())
                .totalPrice(order.getTotalPrice())
                .totalOrderedQuantity(order.getTotalQuantity())
                .summaryItemName(order.getOrderItems().get(0).getItem().getName())
                .orderItemsQuantity(order.getOrderItems().size() - 1)
                .build();
    }
}
