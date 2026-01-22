package com.lhj.myoppingmall.orderItem.entity;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "order_item")
@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "orderItem_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderQuantity;
    private Long orderPrice;
}
