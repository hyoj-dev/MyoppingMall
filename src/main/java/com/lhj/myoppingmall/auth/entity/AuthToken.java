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
    private LocalDateTime issueAt;
    private LocalDateTime expireAt;

    public AuthToken(Long id, Member member, String refreshToken, LocalDateTime issueAt, LocalDateTime expireAt) {
        this.member = member;
        this.refreshToken = refreshToken;
        this.issueAt = issueAt;
        this.expireAt = expireAt;
    }

    public void updateRefreshToken(String refreshToken, LocalDateTime issueAt, LocalDateTime expireAt) {
        this.refreshToken = refreshToken;
        this.issueAt = issueAt;
        this.expireAt = expireAt;
    }

    public void clear() {
        this.refreshToken = null;
        this.issueAt = null;
        this.expireAt = null;
    }

}
