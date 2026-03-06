package com.lhj.myoppingmall.order.controller;

import com.lhj.myoppingmall.auth.security.CustomUserDetails;
import com.lhj.myoppingmall.global.ApiResponseDto;
import com.lhj.myoppingmall.order.dto.OrderListResponseDto;
import com.lhj.myoppingmall.order.dto.create.OrderCreateRequestDto;
import com.lhj.myoppingmall.order.dto.create.OrderCreateResponseDto;
import com.lhj.myoppingmall.order.dto.detail.OrderDetailResponseDto;
import com.lhj.myoppingmall.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /*
     * 주문 생성
     * */
    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
    @PostMapping("/orders")
    public ResponseEntity<ApiResponseDto<OrderCreateResponseDto>> createOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody OrderCreateRequestDto dto
    ) {
        Long buyerId = userDetails.getMemberId();

        OrderCreateResponseDto order = orderService.createOrder(buyerId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDto.created("주문이 생성되었습니다.", order)
        );
    }


    /*
     * 주문 내역 조회
     * */
    @Operation(summary = "주문 내역 조회", description = "주문 내역 리스트를 조회합니다.")
    @GetMapping("/orders")
    public ResponseEntity<ApiResponseDto<OrderListResponseDto>> getOrderList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable
    ) {
        Long buyerId = userDetails.getMemberId();
        OrderListResponseDto orderList = orderService.getOrderList(buyerId, pageable);

        return ResponseEntity.ok(
                ApiResponseDto.ok("성공적으로 조회했습니다.", orderList)
        );
    }

    /*
     * 주문 상세 조회
     * */
    @Operation(summary = "주문 상세 조회", description = "주문 상세 내역을 조회합니다.")
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponseDto<OrderDetailResponseDto>> getOrderDetail(
            @PathVariable(name = "order_Id") Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long buyerId = userDetails.getMemberId();
        OrderDetailResponseDto orderDetail = orderService.getOrderDetail(orderId, buyerId);

        return ResponseEntity.ok(
                ApiResponseDto.ok("성공적으로 조회했습니다.", orderDetail)
        );
    }

    /*
     * 주문 취소
     * */
    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponseDto<Void>> cancelOrder(
            @PathVariable(name = "order_Id") Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long buyerId = userDetails.getMemberId();
        orderService.cancelOrder(orderId, buyerId);

        return ResponseEntity.ok(
                ApiResponseDto.ok("성공적으로 취소되었습니다.", null)
        );
    }
}
