package com.lhj.myoppingmall.member.dto;

import com.lhj.myoppingmall.member.entity.Address;
import com.lhj.myoppingmall.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MemberInfoResponseDto {
    Long memberId;
    String loginId;
    String name;
    String nickname;
    LocalDateTime signupAt;

    String city;
    String street;
    String zipCode;

    public static MemberInfoResponseDto from(Member member) {
        Address address = member.getAddress();

        return MemberInfoResponseDto.builder()
                .memberId(member.getId())
                .loginId(member.getLoginId())
                .name(member.getName())
                .nickname(member.getNickname())
                .signupAt(member.getSignupAt())
                .city(address.getCity())
                .street(address.getStreet())
                .zipCode(address.getZipCode())
                .build();
    }
}