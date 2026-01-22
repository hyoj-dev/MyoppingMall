package com.lhj.myoppingmall.member.entity;

import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String nickname;

    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Item> itemList = new ArrayList<>();

    private LocalDateTime signupAt;

    @Embedded
    private Address address;
}
