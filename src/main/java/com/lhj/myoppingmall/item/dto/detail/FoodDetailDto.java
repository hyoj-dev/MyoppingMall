package com.lhj.myoppingmall.item.dto.detail;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class FoodDetailDto implements ItemDetailDto{
    private String manufacturerCompany;
    private LocalDate expireDate;
    private String description;
}
