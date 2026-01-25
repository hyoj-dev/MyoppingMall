package com.lhj.myoppingmall.item.entity;

import com.lhj.myoppingmall.item.entity.category.Category;
import com.lhj.myoppingmall.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "items")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;
    private Long price;
    private String pictureUrl;
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    private Category category;
    private LocalDateTime registAt;
    private LocalDateTime registerAt;

}
