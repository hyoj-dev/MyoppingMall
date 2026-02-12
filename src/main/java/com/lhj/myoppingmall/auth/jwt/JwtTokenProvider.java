package com.lhj.myoppingmall.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessExpMs;
    private final long refreshExpMs;

    public JwtTokenProvider(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.access-token-exp-seconds}") long accessExpMs,
            @Value("${spring.jwt.refresh-token-exp-second}")long refreshExpMs
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessExpMs = accessExpMs * 1000L;
        this.refreshExpMs = refreshExpMs * 1000L;
    }

    public String createAccessToken(String loginId) {
        Date now = new Date();
        return Jwts.builder()
                .subject(loginId)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessExpMs))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String loginId) {
        Date now = new Date();
        return Jwts.builder()
                .subject(loginId)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + refreshExpMs))
                .signWith(secretKey)
                .compact();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoginId(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

    public Date getExpiration(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
        return claims.getExpiration();
    }
}
