package com.lhj.myoppingmall.auth.service;

import com.lhj.myoppingmall.auth.dto.LoginRequestDto;
import com.lhj.myoppingmall.auth.dto.LoginResponseDto;
import com.lhj.myoppingmall.auth.entity.AuthToken;
import com.lhj.myoppingmall.auth.jwt.JwtTokenProvider;
import com.lhj.myoppingmall.auth.repository.AuthTokenRepository;
import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;
    private AuthTokenRepository authTokenRepository;

    public LoginResponseDto login(LoginRequestDto dto) {
        //사용자 인증 토큰 발급
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.loginId(), dto.password())
        );

        //사용자 정보(loginId) 인증 토큰으로 불러오기
        String loginId = authenticate.getName();
        //loginId를 기준으로 Member 객체 가져오기
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 입니다."));

        //accessToken, refreshToken 발급
        String accessToken = jwtTokenProvider.createAccessToken(loginId);
        String refreshToken = jwtTokenProvider.createRefreshToken(loginId);

        //토큰 발급 시간 및 토큰 유효 시간
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiredAt = toLocalDateTime(jwtTokenProvider.getExpiration(refreshToken));

        //AuthToken 객체 생성
        AuthToken authToken = authTokenRepository.findById(member.getId())
                .orElseGet(() -> new AuthToken(member.getId(), member, refreshToken, issuedAt, expiredAt));

        //생성한 AuthToken 객체에 refreshToken 업데이트
        authToken.updateRefreshToken(refreshToken, issuedAt, expiredAt);
        //AuthToken 객체 DB 저장
        authTokenRepository.save(authToken);

        return new LoginResponseDto("로그인에 성공했습니다.", loginId, accessToken, refreshToken);
    }


    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
