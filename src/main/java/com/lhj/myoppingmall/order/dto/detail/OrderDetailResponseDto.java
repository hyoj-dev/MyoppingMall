package com.lhj.myoppingmall.order.dto.detail;

import com.lhj.myoppingmall.order.entity.Order;
import com.lhj.myoppingmall.order.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Schema(description = "주문 상세 조회 응답 DTO")
public class OrderDetailResponseDto {

    @Schema(description = "주문 ID", example = "1")
    private Long orderId;

    @Schema(description = "주문 상태", example = "ORDERED")
    private OrderStatus orderStatus;

    @Schema(description = "주문 날짜", example = "2026-03-12-15:30")
    private LocalDateTime orderedAt;

    @Schema(description = "총 주문 수량", example = "5")
    private int totalOrderQuantity; //총 주문 수량

    @Schema(description = "총 주문 가격", example = "125000")
    private Long totalOrderPrice;   //총 주문 가격

    @Schema(description = "주문 상품")
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
                .orderedAt(order.getOrderedAt())
                .totalOrderQuantity(totalQuantity)
                .totalOrderPrice(totalPrice)
                .orderItems(items)
                .build();
    }
}
