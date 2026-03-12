package com.lhj.myoppingmall.item.entity.category;

import com.lhj.myoppingmall.item.dto.update.ClothUpdateDto;
import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.member.entity.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("CLOTH")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cloth extends Item {

    private Integer size;
    private String brand;
    private String description;


    /*
    * 자녀 생성 메서드
    * */
    public static Cloth create(
            Member seller,
            String name,
            Long price,
            String pictureUrl,
            Integer stockQuantity,
            Integer size,
            String brand,
            String description
    ) {
        Cloth cloth = new Cloth();
        cloth.assignSeller(seller);
        cloth.setCommon(Category.CLOTH, name, price, pictureUrl, stockQuantity);
        cloth.size = size;
        cloth.brand = brand;
        cloth.description = description;
        return cloth;
    }

    /*
     * 자녀 수정 메서드
     * */
    public void changeDetails(
            Integer size,
            String brand,
            String description
    ) {
        if(size != null) this.size = size;
        if(brand != null) this.brand = brand;
        if(description != null) this.description = description;
    }
}
