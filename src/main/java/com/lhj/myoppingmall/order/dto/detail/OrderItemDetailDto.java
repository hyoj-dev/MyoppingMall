package com.lhj.myoppingmall.order.dto.detail;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.order.entity.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "주문 상품 상세 조회 응답 DTO")
public class OrderItemDetailDto {

    @Schema(description = "주문 상품 ID", example = "1")
    private Long itemId;

    @Schema(description = "주문 상품 명", example = "후드티")
    private String itemName;

    @Schema(description = "주문 상품 사진 URL", example = "https://example/item.jpg")
    private String itemPictureUrl;

    @Schema(description = "그 주문 상품의 수량", example = "2")
    private int itemQuantity;

    @Schema(description = "그 주문 상품의 총 가격", example = "70000")
    private Long itemTotalPrice;    //(orderPrice() * itemQuantity)

    public static OrderItemDetailDto from(OrderItem orderItem) {
        Item item = orderItem.getItem();

        return OrderItemDetailDto.builder()
                .itemId(item.getId())
                .itemName(item.getName())
                .itemPictureUrl(item.getPictureUrl())
                .itemQuantity(orderItem.getOrderQuantity())
                .itemTotalPrice(orderItem.getOrderPrice() * orderItem.getOrderQuantity())
                .build();
    }
}
