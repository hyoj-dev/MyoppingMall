package com.lhj.myoppingmall.item.entity.category;

import com.lhj.myoppingmall.item.entity.Item;

@Entity
@Table(name = "foods")
@DiscriminatorValue("FOOD")
@PrimaryKeyJoinColumn(name = "item_id")
public class Food extends Item {

    private String manufacturerCompany;
    private String description;
}
