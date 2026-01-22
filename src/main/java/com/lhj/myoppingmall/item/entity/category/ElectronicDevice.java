package com.lhj.myoppingmall.item.entity.category;

import com.lhj.myoppingmall.item.entity.Item;

@Entity
@Table(name = "electronicDevices")
@DiscriminatorValue("ELECTRONIC_DEVICE")
@PrimaryKeyJoinColumn(name = "item_id")
public class ElectronicDevice extends Item {

    private String manufacturerCompany;
    private String description;
}
