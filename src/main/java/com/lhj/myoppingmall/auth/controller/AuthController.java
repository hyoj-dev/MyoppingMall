package com.lhj.myoppingmall.auth.controller;

import com.lhj.myoppingmall.auth.dto.LoginRequestDto;
import com.lhj.myoppingmall.auth.dto.LoginResponseDto;
import com.lhj.myoppingmall.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto) {
        return authService.login(dto);
    }
}
