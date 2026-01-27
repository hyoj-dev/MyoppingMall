package com.lhj.myoppingmall.item.dto.detail;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ElectronicDeviceDetailDto implements ItemDetailDto{
    private String manufacturerCompany;
    private Integer warrantyMonths;
    private String description;
}
