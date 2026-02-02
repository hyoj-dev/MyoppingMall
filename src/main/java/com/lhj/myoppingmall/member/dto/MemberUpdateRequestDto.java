package com.lhj.myoppingmall.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class MemberUpdateRequestDto {

    private String nickname;

    private String city;
    private String street;
    private String zipCode;

    public boolean hasAddressChanged() {
        return city != null || street != null || zipCode != null;
    }
}
