package com.lhj.myoppingmall.item.dto.detail;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.item.entity.category.Cloth;
import com.lhj.myoppingmall.item.entity.category.ElectronicDevice;
import com.lhj.myoppingmall.item.entity.category.Food;
import com.lhj.myoppingmall.member.dto.SellerDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "상품 상세 정보 조회 응답 DTO ")
public class ItemDetailResponseDto {

    @Schema(description = "상품 ID", example = "1")
    private Long id;

    @Schema(description = "상품 카테고리", example = "CLOTH")
    private Category category;

    @Schema(description = "상품명", example = "후드티")
    private String name;

    @Schema(description = "상품 가격", example = "35000")
    private Long price;

    @Schema(description = "상품 사진 URL", example = "https://example/item.jpg")
    private String pictureUrl;

    @Schema(description = "남은 상품 개수", example = "5")
    private int stockQuantity;

    @Schema(description = "판매자")
    private SellerDto seller;

    @Schema(description = "카테고리에 따른 상세 항목")
    private ItemDetailDto detail;

    @Schema(description = "상품 비활성화 여부")
    private boolean deleted;

    @Schema(description = "상품 비활성화 시간")
    private LocalDateTime deletedAt;

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
                .deleted(item.isDeleted())
                .deletedAt(item.getDeletedAt())
                .build();
    }
}
