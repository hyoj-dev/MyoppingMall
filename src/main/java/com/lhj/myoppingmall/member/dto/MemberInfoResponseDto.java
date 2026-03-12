package com.lhj.myoppingmall.member.dto;

import com.lhj.myoppingmall.member.entity.Address;
import com.lhj.myoppingmall.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Schema(description = "회원 정보 응답 DTO")
public class MemberInfoResponseDto {

    @Schema(description = "회원 ID", example = "1")
    Long memberId;

    @Schema(description = "로그인 ID", example = "test1234")
    String loginId;

    @Schema(description = "회원 이름", example = "홍길동")
    String name;

    @Schema(description = "닉네임", example = "길동이")
    String nickname;

    @Schema(description = "가입 날짜", example = "2026-03-12-13:30")
    LocalDateTime signupAt;

    @Schema(description = "도시", example = "서울특별시")
    String city;

    @Schema(description = "도로명 주소", example = "가마로 123 1")
    String street;

    @Schema(description = "우편번호", example = "123-123")
    String zipCode;

    public static MemberInfoResponseDto from(Member member) {
        Address address = member.getAddress();

        return MemberInfoResponseDto.builder()
                .memberId(member.getId())
                .loginId(member.getLoginId())
                .name(member.getName())
                .nickname(member.getNickname())
                .signupAt(member.getCreatedAt())
                .city(address == null ? null : address.getCity())
                .street(address == null ? null : address.getStreet())
                .zipCode(address == null ? null : address.getZipCode())
                .build();
    }
}