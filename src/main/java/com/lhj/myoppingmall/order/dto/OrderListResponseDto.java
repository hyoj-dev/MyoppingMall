package com.lhj.myoppingmall.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@Schema(description = "주문 내역 조회 응답 DTO")
public class OrderListResponseDto {

    @Schema(description = "주문 목록 데이터")
    private List<OrderResponseDto> content;

    @Schema(description = "현재 페이지 번호 (0부터 시작)", example = "0")
    private int page;

    @Schema(description = "페이지 당 조회되는 데이터 개수", example = "10")
    private int size;

    @Schema(description = "전체 주문 개수", example = "35")
    private Long totalElement;

    @Schema(description = "전체 페이지 수", example = "4")
    private int totalPages;

    @Schema(description = "다음 페이지 존재 여부", example = "true")
    private boolean hasNext;

    public static OrderListResponseDto from(Page<OrderListProjection> pageResult) {
        return OrderListResponseDto.builder()
                .content(pageResult.getContent().stream()
                        .map(p -> OrderResponseDto.builder()
                                .orderId(p.getOrderId())
                                .orderedAt(p.getOrderedAt())
                                .orderStatus(p.getOrderStatus())
                                .totalOrderQuantity(p.getTotalOrderQuantity())
                                .totalOrderPrice(p.getTotalOrderPrice())
                                .summaryItemName(p.getSummaryItemName())
                                .orderItemsQuantity(p.getOrderItemsQuantity())
                                .build())
                        .toList())
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElement(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .hasNext(pageResult.hasNext())
                .build();
    }
}