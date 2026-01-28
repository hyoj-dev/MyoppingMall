package com.lhj.myoppingmall.order.entity;

import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.orderItem.entity.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
}