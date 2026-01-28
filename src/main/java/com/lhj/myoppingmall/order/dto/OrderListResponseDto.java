package com.lhj.myoppingmall.order.dto;

import com.lhj.myoppingmall.order.entity.Order;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class OrderListResponseDto {
    private List<OrderResponseDto> content;
    private int page;
    private int size;
    private Long totalElement;
    private int totalPages;
    private boolean hasNext;

    public static OrderListResponseDto from(Page<Order> pageResult) {
        return OrderListResponseDto.builder()
                .content(pageResult.getContent().stream()
                        .map(OrderResponseDto::from)
                        .toList())
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElement(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .hasNext(pageResult.hasNext())
                .build();
    }
}