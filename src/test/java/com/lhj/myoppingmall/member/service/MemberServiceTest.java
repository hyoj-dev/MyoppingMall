package com.lhj.myoppingmall.member.service;

import com.lhj.myoppingmall.member.dto.MemberInfoResponseDto;
import com.lhj.myoppingmall.member.dto.MemberUpdateRequestDto;
import com.lhj.myoppingmall.member.entity.Address;
import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    @Transactional
    public void 회원_조회() throws Exception {
        //given
        Member member = Member.create("example", "1234", "홍길동");
        member.changeAddress(new Address("서울특별시", "하나길", "123-123"));

        //when
        Member savedMember = memberRepository.save(member);
        Long findMemberId = memberService.getMember("example").getMemberId();

        //then
        assertThat(savedMember.getId()).isEqualTo(findMemberId);
    }

    @Test
    @Transactional
    public void 회원_정보_변경() throws Exception {
        //given
        Member member = Member.create("example", "1234", "홍길동");
        member.changeNickname("길동이");
        member.changeAddress(new Address("서울특별시", "하나길", "123-123"));

        memberRepository.save(member);

        /*
        * 닉네임과 도시만 변경
        * */
        MemberUpdateRequestDto dto = MemberUpdateRequestDto.builder()
                .nickname("난길동")
                .city("경기도")
                .build();

        //when
        MemberInfoResponseDto response = memberService.updateMember("example", dto);

        //then
        assertThat(response.getNickname()).isEqualTo("난길동");
        assertThat(response.getCity()).isEqualTo("경기도");
    }

    @Test
    @Transactional
    public void 회원_탈퇴() throws Exception {
        //given
        String loginId = "example";
        Member member = Member.create(loginId, "1234", "홍길동");
        memberRepository.save(member);

        //when
        memberService.deleteMember(loginId);

        //then
        assertThat(memberRepository.findByLoginId(loginId)).isEmpty();
    }
}