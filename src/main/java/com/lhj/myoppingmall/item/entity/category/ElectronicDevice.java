package com.lhj.myoppingmall.item.entity.category;

import com.lhj.myoppingmall.item.dto.update.ElectronicDeviceUpdateDto;
import com.lhj.myoppingmall.item.entity.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue("ELECTRONIC_DEVICE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElectronicDevice extends Item {

    private String manufacturerCompany;
    private Integer warrantyMonths;
    private String description;

    /*
    * 자녀 생성 메서드
    * */
    public static ElectronicDevice create(
            Category category,
            String name,
            Long price,
            String pictureUrl,
            Integer stockQuantity,
            String manufacturerCompany,
            Integer warrantyMonths,
            String description
    ) {
        ElectronicDevice ed = new ElectronicDevice();
        ed.setCommon(category, name, price, pictureUrl, stockQuantity);
        ed.manufacturerCompany = manufacturerCompany;
        ed.warrantyMonths = warrantyMonths;
        ed.description = description;
        return ed;
    }

    /*
     * 자녀 필드 수정 메서드
     * */
    public void changeDetails(ElectronicDeviceUpdateDto dto) {
        if(dto == null) return;
        if(dto.getManufacturerCompany() != null) this.manufacturerCompany = dto.getManufacturerCompany();
        if(dto.getWarrantyMonths() != null) this.warrantyMonths = dto.getWarrantyMonths();
        if(dto.getDescription() != null) this.description = dto.getDescription();
    }
}
