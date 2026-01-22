package com.lhj.myoppingmall.item.entity.category;

import com.lhj.myoppingmall.item.entity.Item;

@Entity
@Table(name = "clothes")
@DiscriminatorValue("CLOTH")
@PrimaryKeyJoinColumn(name = "item_id")
public class Cloth extends Item {
    
    private int size;
    private String brand;
    private String description;
}
