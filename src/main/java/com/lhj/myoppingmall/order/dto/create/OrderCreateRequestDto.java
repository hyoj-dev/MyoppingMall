package com.lhj.myoppingmall.order.dto.create;

import com.lhj.myoppingmall.order.dto.OrderItemDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@Schema(description = "주문 생성 요청 DTO")
public class OrderCreateRequestDto {
    @ArraySchema(
            schema = @Schema(implementation = OrderItemDto.class),
            arraySchema = @Schema(
                    description = "주문 상품 목록",
                    example = """
                    [
                      {
                        "itemId": 1,
                        "orderQuantity": 3
                      },
                      {
                        "itemId": 2,
                        "orderQuantity": 2
                      }
                    ]
                    """
            )
    )
    private List<OrderItemDto> orderItems;
}
