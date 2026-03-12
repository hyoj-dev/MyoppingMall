package com.lhj.myoppingmall.item.entity.category;

import com.lhj.myoppingmall.item.dto.update.FoodUpdateDto;
import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.member.entity.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@DiscriminatorValue("FOOD")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends Item {

    private String manufacturerCompany;
    private LocalDate expireDate;
    private String description;


    /*
    * 자녀 생성 메서드
    * */
    public static Food create(
            Member seller,
            String name,
            Long price,
            String pictureUrl,
            Integer stockQuantity,
            String manufacturerCompany,
            LocalDate expireDate,
            String description
    ) {
        Food food = new Food();
        food.assignSeller(seller);
        food.setCommon(Category.FOOD, name, price, pictureUrl, stockQuantity);
        food.manufacturerCompany = manufacturerCompany;
        food.expireDate = expireDate;
        food.description = description;
        return food;
    }

    /*
     * 자녀 필드 수정 메서드
     * */
    public void changeDetails(
            String manufacturerCompany,
            LocalDate expireDate,
            String description
    ) {
        if(manufacturerCompany != null) this.manufacturerCompany = manufacturerCompany;
        if(expireDate != null) this.expireDate = expireDate;
        if(description != null) this.description = description;
    }
}
