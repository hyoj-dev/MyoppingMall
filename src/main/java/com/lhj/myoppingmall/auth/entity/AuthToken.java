package com.lhj.myoppingmall.auth.entity;

import com.lhj.myoppingmall.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "auth_tokens")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthToken {

    @Id
    @Column(name = "member_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String refreshToken;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;

    public AuthToken(Member member, String refreshToken, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.member = member;
        this.refreshToken = refreshToken;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public void updateRefreshToken(String refreshToken, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.refreshToken = refreshToken;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public void clear() {
        this.refreshToken = null;
        this.issuedAt = null;
        this.expiredAt = null;
    }

}
