package com.lhj.myoppingmall.item.dto.detail;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.item.entity.category.Cloth;
import com.lhj.myoppingmall.item.entity.category.ElectronicDevice;
import com.lhj.myoppingmall.item.entity.category.Food;
import com.lhj.myoppingmall.member.dto.SellerDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDetailResponseDto {

    private Long id;
    private Category category;
    private String name;
    private Long price;
    private String pictureUrl;
    private int stockQuantity;

    private SellerDto seller;
    private ItemDetailDto detail;

    public static ItemDetailResponseDto from(Item item) {
        SellerDto sellerDto = SellerDto.builder()
                .memberId(item.getSeller().getId())
                .nickname(item.getSeller().getNickname())
                .build();

        ItemDetailDto detail = switch (item.getCategory()) {
            case CLOTH -> {
                Cloth c = (Cloth) item;
                yield ClothDetailDto.builder()
                        .size(c.getSize())
                        .brand(c.getBrand())
                        .description(c.getDescription())
                        .build();
            }
            case FOOD -> {
                Food f = (Food) item;
                yield FoodDetailDto.builder()
                        .manufacturerCompany(f.getManufacturerCompany())
                        .expireDate(f.getExpireDate())
                        .description(f.getDescription())
                        .build();
            }
            case ELECTRONIC_DEVICE -> {
                ElectronicDevice e = (ElectronicDevice) item;
                yield ElectronicDeviceDetailDto.builder()
                        .manufacturerCompany(e.getManufacturerCompany())
                        .warrantyMonths(e.getWarrantyMonths())
                        .description(e.getDescription())
                        .build();
            }
        };

        return ItemDetailResponseDto.builder()
                .id(item.getId())
                .category(item.getCategory())
                .name(item.getName())
                .price(item.getPrice())
                .pictureUrl(item.getPictureUrl())
                .stockQuantity(item.getStockQuantity())
                .seller(sellerDto)
                .detail(detail)
                .build();
    }
}
