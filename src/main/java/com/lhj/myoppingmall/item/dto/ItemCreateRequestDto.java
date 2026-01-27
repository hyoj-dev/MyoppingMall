package com.lhj.myoppingmall.item.dto;

import com.lhj.myoppingmall.item.entity.category.Category;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ItemCreateRequestDto {
    private Category category;
    private String name;
    private Long price;
    private String pictureUrl;
    private int stockQuantity;

    //Cloth
    private Integer size;
    private String brand;

    //Food
    private LocalDate expireDate;

    //Electronic_Device
    private int warrantyMonths;

    //Food & Electronic_Device
    private String manufacturerCompany;

    //Cloth & Food & Electronic_Device
    private String description;

}
