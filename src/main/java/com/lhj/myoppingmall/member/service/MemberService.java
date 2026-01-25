package com.lhj.myoppingmall.member.service;

import com.lhj.myoppingmall.member.dto.MemberInfoResponseDto;
import com.lhj.myoppingmall.member.dto.MemberPatchRequestDto;
import com.lhj.myoppingmall.member.entity.Address;
import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 조회
    public MemberInfoResponseDto getMember(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));
        return MemberInfoResponseDto.from(member);
    }

    //회원 정보 변경
    @Transactional
    public MemberInfoResponseDto patchMember(String loginId, MemberPatchRequestDto dto) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        if (dto.getNickname() != null) {
            member.changeNickname(dto.getNickname());
        }

        if (dto.hasAddressChanged()) {
            Address old = member.getAddress();

            Address newAddress = new Address(
                    dto.getCity() != null ? dto.getCity() : old.getCity(),
                    dto.getStreet() != null ? dto.getStreet() : old.getStreet(),
                    dto.getZipCode() != null ? dto.getZipCode() : old.getZipCode()
            );
            member.changeAddress(newAddress);
        }
        return MemberInfoResponseDto.from(member);
    }

    //회원 탈퇴
    public void deleteMember(Long memberId, String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        memberRepository.delete(member);
    }
}
