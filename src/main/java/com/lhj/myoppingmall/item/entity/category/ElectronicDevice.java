package com.lhj.myoppingmall.item.entity.category;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.member.entity.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
            Member seller,
            String name,
            Long price,
            String pictureUrl,
            Integer stockQuantity,
            String manufacturerCompany,
            Integer warrantyMonths,
            String description
    ) {
        ElectronicDevice ed = new ElectronicDevice();
        ed.assignSeller(seller);
        ed.setCommon(Category.ELECTRONIC_DEVICE, name, price, pictureUrl, stockQuantity);
        ed.manufacturerCompany = manufacturerCompany;
        ed.warrantyMonths = warrantyMonths;
        ed.description = description;
        return ed;
    }

    /*
     * 자녀 필드 수정 메서드
     * */
    public void changeDetails(
            String manufacturerCompany,
            Integer warrantyMonths,
            String description
    ) {
        if(manufacturerCompany != null) this.manufacturerCompany = manufacturerCompany;
        if(warrantyMonths != null) this.warrantyMonths = warrantyMonths;
        if(description != null) this.description = description;
    }
}
