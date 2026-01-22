package com.lhj.myoppingmall.item.entity;

import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Table(name = "items")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;
    private Long price;
    private String pictureUrl;
    private int stockQuantity;

    private Category category;
    private LocalDateTime registAt;

}
