package com.lhj.myoppingmall.member.controller;

import com.lhj.myoppingmall.auth.security.CustomUserDetails;
import com.lhj.myoppingmall.global.ApiResponseDto;
import com.lhj.myoppingmall.member.dto.MemberInfoResponseDto;
import com.lhj.myoppingmall.member.dto.MemberSignupRequestDto;
import com.lhj.myoppingmall.member.dto.MemberSignupResponseDto;
import com.lhj.myoppingmall.member.dto.MemberUpdateRequestDto;
import com.lhj.myoppingmall.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*
     * 회원가입
     * */
    @PostMapping("/members")
    public ResponseEntity<ApiResponseDto<MemberSignupResponseDto>> signup(
            @Valid @RequestBody MemberSignupRequestDto dto
    ) {
        MemberSignupResponseDto response = memberService.signup(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDto.created("회원가입이 완료되었습니다.", response)
        );
    }

    /*
    * 내 정보 조회 (마이페이지)
    * */
    @GetMapping("/members/me")
    public ResponseEntity<ApiResponseDto<MemberInfoResponseDto>> getMyPage(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long memberId = userDetails.getMemberId();
        MemberInfoResponseDto memberInfo = memberService.getMyPage(memberId);

        return ResponseEntity.ok(
                ApiResponseDto.ok("성공적으로 조회했습니다.", memberInfo)
        );
    }

    /*
     * 회원 정보 변경
     * */
    @PatchMapping("/members/me")
    public ResponseEntity<ApiResponseDto<MemberInfoResponseDto>> updateMember(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody MemberUpdateRequestDto dto
    ) {
        Long memberId = userDetails.getMemberId();
        MemberInfoResponseDto updatedMember = memberService.updateMember(memberId, dto);

        return ResponseEntity.ok(
                ApiResponseDto.ok("성공적으로 변경했습니다.", updatedMember)
        );
    }

    /*
     * 회원 탈퇴
     * */
    @DeleteMapping("/members/me")
    public ResponseEntity<ApiResponseDto<Void>> deleteMember(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long memberId = userDetails.getMemberId();
        memberService.deleteMember(memberId);

        return ResponseEntity.ok(
                ApiResponseDto.ok("성공적으로 탈퇴되었습니다.", null)
        );
    }
}