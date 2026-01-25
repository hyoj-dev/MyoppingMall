package com.lhj.myoppingmall.item.dto.update;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FoodUpdateDto {
    private String manufacturerCompany;
    private LocalDate expireDate;
    private String description;
}
