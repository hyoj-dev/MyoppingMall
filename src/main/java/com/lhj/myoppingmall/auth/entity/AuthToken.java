package com.lhj.myoppingmall.auth.entity;

import com.lhj.myoppingmall.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Table(name = "auth_tokens")
@Entity
@Getter
public class AuthToken {

    @Id @GeneratedValue
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String refreshToken;
    private LocalDateTime issueAt;
    private LocalDateTime expireAt;
}
