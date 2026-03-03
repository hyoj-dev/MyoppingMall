package com.lhj.myoppingmall.order.service;

import com.lhj.myoppingmall.global.exception.CustomException;
import com.lhj.myoppingmall.global.exception.ErrorCode;
import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.item.repository.ItemRepository;
import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import com.lhj.myoppingmall.order.dto.*;
import com.lhj.myoppingmall.order.dto.create.OrderCreateRequestDto;
import com.lhj.myoppingmall.order.dto.create.OrderCreateResponseDto;
import com.lhj.myoppingmall.order.dto.detail.OrderDetailResponseDto;
import com.lhj.myoppingmall.order.entity.Order;
import com.lhj.myoppingmall.order.repository.OrderRepository;
import com.lhj.myoppingmall.order.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;


    /*
    * 주문 생성
    * */
    @Transactional
    public OrderCreateResponseDto createOrder(Long buyerId, OrderCreateRequestDto dto) {
        Member buyer = memberRepository.findById(buyerId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        List<OrderItem> orderItemEntities = new ArrayList<>();

        for (OrderItemDto itemDto : dto.getOrderItems()) {
            Long orderedItemId = itemDto.getItemId();
            int orderedQuantity = itemDto.getOrderQuantity();

            Item orderedItem = itemRepository.findById(orderedItemId)
                    .orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

            OrderItem orderItem = OrderItem.createOrderItem(orderedItem, orderedQuantity, orderedItem.getPrice());

            orderItemEntities.add(orderItem);
        }

        Order order = Order.createOrder(buyer, orderItemEntities);
        orderRepository.save(order);

        return OrderCreateResponseDto.from(order.getId(), order.getTotalQuantity(), order.getTotalPrice());
    }

    /*
     * 주문 내역 조회
     * */
    public OrderListResponseDto getOrderList(Pageable pageable) {
        Page<Order> pageResult = orderRepository.findAll(pageable);
        return OrderListResponseDto.from(pageResult);
    }

    /*
     * 주문 상세 조회
     * */
    public OrderDetailResponseDto getOrderDetail(Long orderId, Long buyerId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));

        if (!order.getBuyer().getId().equals(buyerId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        return OrderDetailResponseDto.from(order);
    }

    /*
     * 주문 취소
     * */
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        if (!order.getBuyer().getId().equals(buyerId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);

        order.cancelOrder();
    }

    /*
     * 주문 검색 공통 메서드
     * */
    private Order findOrderByOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }
}