package com.lhj.myoppingmall.order.dto.create;

import com.lhj.myoppingmall.order.dto.OrderItemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
//@NoArgsConstructor
@Builder
public class OrderCreateRequestDto {
    private List<OrderItemDto> orderItems;
}
