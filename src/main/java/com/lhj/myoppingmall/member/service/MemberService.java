package com.lhj.myoppingmall.member.service;

import com.lhj.myoppingmall.auth.repository.AuthTokenRepository;
import com.lhj.myoppingmall.global.exception.CustomException;
import com.lhj.myoppingmall.global.exception.ErrorCode;
import com.lhj.myoppingmall.item.entity.Item;
import com.lhj.myoppingmall.item.repository.ItemRepository;
import com.lhj.myoppingmall.member.dto.MemberInfoResponseDto;
import com.lhj.myoppingmall.member.dto.MemberSignupRequestDto;
import com.lhj.myoppingmall.member.dto.MemberSignupResponseDto;
import com.lhj.myoppingmall.member.dto.MemberUpdateRequestDto;
import com.lhj.myoppingmall.member.entity.Address;
import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import com.lhj.myoppingmall.order.entity.Order;
import com.lhj.myoppingmall.order.repository.OrderRepository;
import com.lhj.myoppingmall.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final AuthTokenRepository authTokenRepository;
    private final PasswordEncoder passwordEncoder;

    /*
    * 회원 가입
    * */
    public MemberSignupResponseDto signup(MemberSignupRequestDto dto) {
        //loginId 중복 체크
        memberRepository.findByLoginId(dto.loginId()).ifPresent(m ->{
            throw new IllegalArgumentException("이미 사용중인 loginId 입니다.");
        });

        String encodedPw = passwordEncoder.encode(dto.password());

        Member member = Member.create(
                dto.loginId(),
                encodedPw,
                dto.name(),
                dto.nickname(),
                new Address(dto.city(), dto.street(), dto.zipCode())
        );

        Member saved = memberRepository.save(member);

        return new MemberSignupResponseDto(saved.getId(), saved.getLoginId());
    }

    /*
    * 내 정보 조회 (마이페이지)
    * */
    @Transactional(readOnly = true)
    public MemberInfoResponseDto getMyPage(Long memberId) {
        Member member = findMemberById(memberId);

        return MemberInfoResponseDto.from(member);
    }

    /*
    * 회원 정보 변경
    * */
    public MemberInfoResponseDto updateMember(Long memberId, MemberUpdateRequestDto dto) {
        Member member = findMemberById(memberId);

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

    /*
    * 회원 탈퇴
    * */
    public void deleteMember(Long memberId) {
        Member member = findActiveMemberById(memberId);

        cancelMemberOrders(memberId);
        deactivateMemberItems(memberId);
        deleteAuthToken(memberId);

        member.softDelete();
    }

    /*
    * Member 찾기 공통 메서드
    * */
    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    /*
    * 회원의 주문 취소
    * */
    private void cancelMemberOrders(Long memberId) {
        List<Order> orders = orderRepository.findAllByBuyer_Id(memberId);
        for (Order order : orders) {
            orderService.cancelOrder(order.getId(), memberId);

        }
    }

    /*
     * 회원의 상품 soft delete
     * */
    private void deactivateMemberItems(Long memberId) {
        List<Item> items = itemRepository.findAllBySeller_Id(memberId);
        for (Item item : items) {
            item.softDelete();
        }
    }


    /*
    * 회원의 AuthToken 삭제
    * */
    private void deleteAuthToken(Long memberId) {
        authTokenRepository.deleteById(memberId);
    }

    private Member findActiveMemberById(Long memberId) {
        Member member = findMemberById(memberId);

        if (member.isDeleted()) {
            throw new CustomException(ErrorCode.MEMBER_DELETED);
        }

        return member;
    }
}