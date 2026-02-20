package com.lhj.myoppingmall.auth.service;

import com.lhj.myoppingmall.auth.dto.*;
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

    /*
    * 로그인
    * */
    @Transactional
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

    /*
    * 토큰 재발급
    * */
    @Transactional
    public ReissueResponseDto reissue(ReissueRequestDto dto) {
        String refreshToken = dto.refreshToken();

        //RefreshToken 유효성 검증
        if (!jwtTokenProvider.validate(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token입니다.");
        }

        //refreshToken으로 loginId 찾기
        String loginId = jwtTokenProvider.getLoginId(refreshToken);
        //loginId를 기준으로 Member 객체 가져오기
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        //Member.id == AuthToken.id 이므로 Member.id로 DB에 저장되어있는 토큰 찾기
        AuthToken savedAuthToken = authTokenRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 토큰입니다."));

        //DB에 저장된 refresh와 비교 (탈취/교체 방지)
        if (savedAuthToken.getRefreshToken() == null || !savedAuthToken.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("Refresh Token이 다릅니다.");
        }

        //DB 만료 체크
        if (savedAuthToken.getExpiredAt() != null || savedAuthToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("만료된 Token 입니다.");
        }

        //새로운 accessToken 발급
        String newAccessToken = jwtTokenProvider.createAccessToken(loginId);
        //새로운 refreshToken 발급 및 업데이트
        String newRefreshToken = jwtTokenProvider.createRefreshToken(loginId);
        savedAuthToken.updateRefreshToken(newRefreshToken, LocalDateTime.now(), toLocalDateTime(jwtTokenProvider.getExpiration(newRefreshToken)));

        return new ReissueResponseDto("재발급 성공", newAccessToken, newRefreshToken);
    }

    /*
     * 로그아웃
     * RefreshToken을 서버에서 폐기해서 더 이상 재발급이 안 되도록 만들기
     * */
    @Transactional
    public void logout(LogoutRequestDto dto) {
        AuthToken authToken = authTokenRepository.findByRefreshToken(dto.refreshToken())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 refreshToken 입니다."));

        authTokenRepository.delete(authToken);
    }


    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
