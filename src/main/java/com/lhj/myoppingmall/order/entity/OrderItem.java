package com.lhj.myoppingmall.order.entity;

import com.lhj.myoppingmall.item.entity.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "order_item")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private int orderQuantity;  //상품 수량
    private Long orderPrice;    //주문 당시 상품 가격(1개)

    //==연관관계 메서드==
    public void assignOrder(Order order) {
        this.order = order;
    }

    //==생성 메서드==
    public static OrderItem createOrderItem(
            Item item,
            int orderQuantity,
            Long orderPrice
    ) {
        OrderItem orderItem = new OrderItem();
        orderItem.item = item;
        orderItem.orderQuantity = orderQuantity;
        orderItem.orderPrice = orderPrice;
        return orderItem;
    }

    /*
     * 주문 취소 메서드
     * */
    public void cancelOrder() {
        getItem().addStock(this.orderQuantity);
    }
}
