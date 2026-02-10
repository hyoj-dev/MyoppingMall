package com.lhj.myoppingmall.member.dto;

public record MemberSignupRequestDto(
        String loginId,
        String password,
        String name
) { }
