package com.lhj.myoppingmall.order.dto.detail;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.order.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemDetailDto {
    private Long itemId;    //주문 상품 ID
    private String itemName;    //주문 상품명
    private String itemPictureUrl;  //주문 상품 사진 URL
    private int itemQuantity;   //그 주문 상품의 수량
    private Long itemTotalPrice;    //그 주문 상품의 총 가격 (orderPrice() * itemQuantity)

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
