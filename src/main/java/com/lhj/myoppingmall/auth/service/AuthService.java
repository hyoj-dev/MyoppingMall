package com.lhj.myoppingmall.auth.service;

import com.lhj.myoppingmall.auth.dto.LoginRequestDto;
import com.lhj.myoppingmall.auth.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public LoginResponseDto login(LoginRequestDto dto) {
        //
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.loginId(), dto.password())
        );

        String loginId = authenticate.getName();

        return new LoginResponseDto("로그인에 성공했습니다.", loginId);
    }
}
