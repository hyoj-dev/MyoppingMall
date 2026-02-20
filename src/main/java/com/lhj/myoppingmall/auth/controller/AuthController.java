package com.lhj.myoppingmall.auth.controller;

import com.lhj.myoppingmall.auth.dto.*;
import com.lhj.myoppingmall.auth.service.AuthService;
import com.lhj.myoppingmall.global.ApiResponseDto;
import com.lhj.myoppingmall.member.dto.MemberSignupRequestDto;
import com.lhj.myoppingmall.member.dto.MemberSignupResponseDto;
import com.lhj.myoppingmall.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;


    /*
     * 회원가입
     * */
    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponseDto<MemberSignupResponseDto>> signup(
            @RequestBody MemberSignupRequestDto dto
    ) {
        MemberSignupResponseDto response = memberService.signup(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDto<>(201, "회원가입이 완료되었습니다.", response)
        );
    }

    /*
     * 로그인
     * */
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto dto) {
        LoginResponseDto login = authService.login(dto);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "로그인이 완료되었습니다.", login)
        );
    }

    /*
     * 토큰 재발급
     * */
    @PostMapping("/auth/reissue")
    public ResponseEntity<ApiResponseDto<ReissueResponseDto>> reissue(@RequestBody ReissueRequestDto dto) {
        ReissueResponseDto reissue = authService.reissue(dto);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "refreshToken 재발급이 완료되었습니다.", reissue)
        );
    }

    /*
     * 로그아웃
     * */
    @PostMapping("/auth/logout")
    public ResponseEntity<ApiResponseDto<Void>> logout(@RequestBody LogoutRequestDto dto) {
        authService.logout(dto);

        return ResponseEntity.ok(
                new ApiResponseDto<>(200, "로그아웃이 완료되었습니다.", null)
        );
    }
}
