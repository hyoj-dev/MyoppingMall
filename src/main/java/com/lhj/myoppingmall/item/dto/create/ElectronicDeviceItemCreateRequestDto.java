package com.lhj.myoppingmall.item.dto.create;

import com.lhj.myoppingmall.item.entity.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "전자기기 상품 등록 요청")
public class ElectronicDeviceItemCreateRequestDto extends ItemCreateRequestDto {

    @NotBlank
    @Schema(description = "전자기기 제조사", example = "Apple")
    private String manufacturerCompany;

    @NotNull
    @Schema(description = "보증 기간(달)", example = "12")
    private int warrantyMonths;
}
