package com.lhj.myoppingmall.auth.controller;

import com.lhj.myoppingmall.auth.dto.*;
import com.lhj.myoppingmall.auth.service.AuthTokenService;
import com.lhj.myoppingmall.global.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "로그인", description = "로그인을 진행합니다.")
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto dto) {
        LoginResponseDto login = authTokenService.login(dto);

        return ResponseEntity.ok(
                ApiResponseDto.ok("로그인에 성공했습니다.", login)
        );
    }

    /*
     * 토큰 재발급
     * */
    @Operation(summary = "토큰 재발급", description = "토큰을 재발급합니다.")
    @PostMapping("/auth/reissue")
    public ResponseEntity<ApiResponseDto<ReissueResponseDto>> reissue(@RequestBody ReissueRequestDto dto) {
        ReissueResponseDto reissue = authTokenService.reissue(dto);

        return ResponseEntity.ok(
                ApiResponseDto.ok("토큰이 재발급되었습니다.", reissue)
        );
    }

    /*
     * 로그아웃
     * */
    @Operation(summary = "로그아웃", description = "로그아웃합니다")
    @PostMapping("/auth/logout")
    public ResponseEntity<ApiResponseDto<Void>> logout(@RequestBody LogoutRequestDto dto) {
        authTokenService.logout(dto);

        return ResponseEntity.ok(
                ApiResponseDto.ok("로그아웃 되었습니다.", null)
        );
    }
}
