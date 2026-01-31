package com.lhj.myoppingmall.item.entity.category;

import com.lhj.myoppingmall.item.dto.update.ClothUpdateDto;
import com.lhj.myoppingmall.item.entity.Item;
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
            String name,
            Long price,
            String pictureUrl,
            Integer stockQuantity,
            Integer size,
            String brand,
            String description
    ) {
        Cloth cloth = new Cloth();
        cloth.setCommon(Category.CLOTH, name, price, pictureUrl, stockQuantity);
        cloth.size = size;
        cloth.brand = brand;
        cloth.description = description;
        return cloth;
    }

    /*
     * 자녀 수정 메서드
     * */
    public void changeDetails(ClothUpdateDto dto) {
        if(dto == null) return;
        if(dto.getSize() != null) this.size = dto.getSize();
        if(dto.getBrand() != null) this.brand = dto.getBrand();
        if(dto.getDescription() != null) this.description = dto.getDescription();
    }
}
