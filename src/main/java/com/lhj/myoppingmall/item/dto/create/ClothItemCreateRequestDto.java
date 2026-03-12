package com.lhj.myoppingmall.item.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "ClothItemCreateRequestDto", description = "의류 상품 등록 요청")
public class ClothItemCreateRequestDto extends ItemCreateRequestDto {

    @NotNull
    @Schema(description = "의류 사이즈", example = "105")
    private Integer size;

    @NotBlank
    @Schema(description = "의류 브랜드", example = "NIKE")
    private String brand;


}
