package com.lhj.myoppingmall.order.entity;

import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.orderItem.entity.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.lhj.myoppingmall.order.entity.OrderStatus.CANCELED;
import static com.lhj.myoppingmall.order.entity.OrderStatus.ORDERED;

@Table(name = "orders")
@Entity
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member buyer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private Integer totalQuantity;  //총 주문 상품 수량
    private Long totalPrice;    //총 주문 가격

    private LocalDateTime orderAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //==연관관계 메서드==
    private void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.assignOrder(this);
    }

    //==생성 메서드==
    public static Order createOrder(
            Member buyer,
            List<OrderItem> orderItems
    ) {
        Order order = new Order();
        order.buyer = buyer;
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.totalQuantity = calcTotalQuantity(orderItems);
        order.totalPrice = calcTotalPrice(orderItems);
        order.orderStatus = ORDERED;

        return order;
    }

    //==비즈니스 로직==
    
    /*
    * 전체 주문 상품 수량 계산 메서드
    * */
    private static Integer calcTotalQuantity(List<OrderItem> orderItems) {
        int result = 0;
        if (!orderItems.isEmpty()) {
            for (OrderItem orderItem : orderItems) {
                result += orderItem.getOrderQuantity();
            }
        }
        return result;
    }

    /*
    * 전체 주문 가격 계산 메서드
    * */
    private static Long calcTotalPrice(List<OrderItem> orderItems) {
        int result = 0;
        if (!orderItems.isEmpty()) {
            for (OrderItem orderItem : orderItems) {
                result += (int) (orderItem.getOrderPrice() * orderItem.getOrderQuantity());
            }
        }
        return (long) result;
    }
}