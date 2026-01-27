package com.lhj.myoppingmall.member.entity;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String nickname;

    @OneToMany(mappedBy = "member", fetch = LAZY)
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "seller", fetch = LAZY)
    private List<Item> itemList = new ArrayList<>();

    private LocalDateTime signupAt;

    @Embedded
    private Address address;

    //====유저 정보 변경 메소드====
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeAddress(Address address) {
        this.address = address;
    }
}
