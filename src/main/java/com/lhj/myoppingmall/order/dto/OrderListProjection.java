package com.lhj.myoppingmall.order.dto;

import com.lhj.myoppingmall.order.entity.OrderStatus;

import java.time.LocalDateTime;

public interface OrderListProjection {

    Long getOrderId();
    LocalDateTime getOrderedAt();
    OrderStatus getOrderStatus();
    int getTotalOrderQuantity();
    Long getTotalOrderPrice();
    String getSummaryItemName();
    int getOrderItemsQuantity();
}
