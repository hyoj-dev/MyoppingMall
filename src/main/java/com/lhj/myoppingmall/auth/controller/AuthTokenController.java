package com.lhj.myoppingmall.auth.controller;

import com.lhj.myoppingmall.auth.dto.*;
import com.lhj.myoppingmall.auth.service.AuthTokenService;
import com.lhj.myoppingmall.global.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthTokenController {

    private final AuthTokenService authTokenService;

    /*
     * 로그인
     * */
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto dto) {
        LoginResponseDto login = authTokenService.login(dto);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "로그인이 완료되었습니다.", login)
        );
    }

    /*
     * 토큰 재발급
     * */
    @PostMapping("/auth/reissue")
    public ResponseEntity<ApiResponseDto<ReissueResponseDto>> reissue(@RequestBody ReissueRequestDto dto) {
        ReissueResponseDto reissue = authTokenService.reissue(dto);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "refreshToken 재발급이 완료되었습니다.", reissue)
        );
    }

    /*
     * 로그아웃
     * */
    @PostMapping("/auth/logout")
    public ResponseEntity<ApiResponseDto<Void>> logout(@RequestBody LogoutRequestDto dto) {
        authTokenService.logout(dto);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "로그아웃이 완료되었습니다.", null)
        );
    }
}
