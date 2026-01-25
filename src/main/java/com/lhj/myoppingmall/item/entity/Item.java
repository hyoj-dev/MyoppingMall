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
    private LocalDateTime registerAt;

    //==편의 메서드==

    /*
    * save 시점 자동 호출
    * */
    @PrePersist
    protected void onCreate() {
        this.registerAt = LocalDateTime.now();
    }

    /*
    * 공통 필드 설정 메서드
    * */
    public void setCommon(Category category, String name, Long price, String pictureUrl, Integer stockQuantity) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.stockQuantity = stockQuantity;
    }

    /*
     * 공통 수정 메서드
     * */
    public void changeCommon(String name, Long price, String pictureUrl, Integer stockQuantity) {
        if (name != null) this.name = name;
        if (price != null) this.price = price;
        if (pictureUrl != null) this.pictureUrl = pictureUrl;
        if (stockQuantity != null) this.stockQuantity = stockQuantity;
    }
}
