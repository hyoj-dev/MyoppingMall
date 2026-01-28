package com.lhj.myoppingmall.order.dto.detail;

import com.lhj.myoppingmall.order.entity.Order;
import com.lhj.myoppingmall.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderDetailResponseDto {
    private Long orderId;
    private OrderStatus orderStatus;
    private LocalDateTime orderedAt;
    private int totalOrderQuantity; //총 주문 수량
    private Long totalOrderPrice;   //총 주문 가격
    private List<OrderItemDetailDto> orderItems;    //주문 상품

    public static OrderDetailResponseDto from(Order order) {
        List<OrderItemDetailDto> items = order.getOrderItems().stream()
                .map(OrderItemDetailDto::from)
                .toList();

        int totalQuantity = items.stream()
                .mapToInt(OrderItemDetailDto::getItemQuantity)
                .sum();

        long totalPrice = items.stream()
                .mapToLong(OrderItemDetailDto::getItemTotalPrice)
                .sum();

        return OrderDetailResponseDto.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .orderedAt(order.getOrderAt())
                .totalOrderQuantity(totalQuantity)
                .totalOrderPrice(totalPrice)
                .orderItems(items)
                .build();
    }
}
