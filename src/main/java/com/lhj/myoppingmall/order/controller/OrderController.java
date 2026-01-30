package com.lhj.myoppingmall.order.controller;

import com.lhj.myoppingmall.global.ApiResponseDto;
import com.lhj.myoppingmall.member.service.MemberService;
import com.lhj.myoppingmall.order.dto.OrderListResponseDto;
import com.lhj.myoppingmall.order.dto.create.OrderCreateRequestDto;
import com.lhj.myoppingmall.order.dto.create.OrderCreateResponseDto;
import com.lhj.myoppingmall.order.dto.detail.OrderDetailResponseDto;
import com.lhj.myoppingmall.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /*
     * 주문 생성
     * */
    @PostMapping("/orders")
    public ResponseEntity<ApiResponseDto<OrderCreateResponseDto>> createOrder(
            @RequestParam Long buyerId,
            @RequestBody OrderCreateRequestDto dto
    ) {
        OrderCreateResponseDto order = orderService.createOrder(buyerId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDto<>(201, "주문이 성공적으로 완료되었습니다.", order)
        );
    }


    /*
     * 주문 내역 조회
     * */
    @GetMapping("/orders")
    public ResponseEntity<ApiResponseDto<OrderListResponseDto>> getOrderList(
            Pageable pageable
    ) {
        OrderListResponseDto orderList = orderService.getOrderList(pageable);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "주문 내역 조회를 성공했습니다.", orderList)
        );
    }

    /*
     * 주문 상세 조회
     * */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponseDto<OrderDetailResponseDto>> getOrderDetail(
            @PathVariable Long orderId,
            @RequestParam Long memberId
    ) {
        OrderDetailResponseDto orderDetail = orderService.getOrderDetail(orderId, memberId);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "주문 상세 조회를 성공했습니다.", orderDetail)
        );
    }

    /*
     * 주문 취소
     * */
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponseDto<Void>> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "주문이 취소되었습니다.", null)
        );
    }
}
