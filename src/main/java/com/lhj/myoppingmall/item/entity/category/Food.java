package com.lhj.myoppingmall.item.entity.category;

import com.lhj.myoppingmall.item.entity.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "foods")
@DiscriminatorValue("FOOD")
@PrimaryKeyJoinColumn(name = "item_id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends Item {

    private String manufacturerCompany;
    private String description;
}
