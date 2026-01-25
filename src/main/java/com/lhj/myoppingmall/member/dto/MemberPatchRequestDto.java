package com.lhj.myoppingmall.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberPatchRequestDto {

    private String nickname;

    private String city;
    private String street;
    private String zipCode;

    public boolean hasAddressChanged() {
        return city != null || street != null || zipCode != null;
    }
}
