package com.lhj.myoppingmall.item.dto.create;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.lhj.myoppingmall.item.entity.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "category",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClothItemCreateRequestDto.class, name = "CLOTH"),
        @JsonSubTypes.Type(value = FoodItemCreateRequestDto.class, name = "FOOD"),
        @JsonSubTypes.Type(value = ElectronicDeviceItemCreateRequestDto.class, name = "ELECTRONIC_DEVICE")

})
@Schema(
        description = "상품 등록 요청",
        discriminatorProperty = "category",
        oneOf = {
                ClothItemCreateRequestDto.class,
                FoodItemCreateRequestDto.class,
                ElectronicDeviceItemCreateRequestDto.class
        }
)
public abstract class ItemCreateRequestDto {

    @NotNull
    @Schema(description = "상품 카테고리", example = "CLOTH")
    protected Category category;

    @NotBlank
    @Schema(description = "등록 상품명", example = "후드티")
    protected String name;

    @NotNull
    @Schema(description = "상품 등록 가격", example = "35000")
    protected Long price;

    @Schema(description = "등록 상품 이미지 URL", example = "https://example.com/item.jpg")
    protected String pictureUrl;

    @NotNull
    @Schema(description = "상품 등록 수량", example = "5")
    protected Integer stockQuantity;

    @Schema(description = "상품 설명", example = "평범한 후드티")
    protected String description;
}
