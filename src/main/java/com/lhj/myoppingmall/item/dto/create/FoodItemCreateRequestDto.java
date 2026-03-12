package com.lhj.myoppingmall.item.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Schema(description = "음식 상품 등록 요청")
public class FoodItemCreateRequestDto extends ItemCreateRequestDto {

    @NotBlank
    @Schema(description = "음식 제조사", example = "마음 농원")
    private String manufacturerCompany;

    @NotNull
    @Schema(description = "유통기한", example = "2026-03-12")
    private LocalDate expireDate;
}
