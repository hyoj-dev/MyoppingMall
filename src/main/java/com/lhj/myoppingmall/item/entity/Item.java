package com.lhj.myoppingmall.item.entity;

import com.lhj.myoppingmall.global.BaseTimeEntity;
import com.lhj.myoppingmall.global.exception.CustomException;
import com.lhj.myoppingmall.global.exception.ErrorCode;
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
public abstract class Item extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member seller;

    private String name;
    private Long price;
    private String pictureUrl;
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    private LocalDateTime deletedAt;

    //==편의 메서드==
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

    /*
     * 판매자 위임 메서드
     * */
    protected void assignSeller(Member seller) {
        this.seller = seller;
    }

    /*
     * 주문 생성시 수량 감소 메서드
     * */
    public void removeStock(int orderQuantity) {
        if (orderQuantity <= 0) {
            throw new CustomException(ErrorCode.INVALID_STOCK_QUANTITY);
        }

        if (this.stockQuantity < orderQuantity) {
            throw new CustomException(ErrorCode.OUT_OF_STOCK);
        }

        this.stockQuantity -= orderQuantity;
    }

    /*
    * 주문 취소시 수량 증가 메서드
    * */
    public void addStock(int orderQuantity) {
        if (orderQuantity <= 0) {
            throw new CustomException(ErrorCode.INVALID_STOCK_QUANTITY);
        }

        this.stockQuantity += orderQuantity;
    }

    /*
    * 상품 soft delete
    * */
    public void softDelete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
