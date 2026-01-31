package com.lhj.myoppingmall.member.repository;

import com.lhj.myoppingmall.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void 회원등록_및_조회() throws Exception {
        //given
        Member member = Member.create("exampleId", "1234", "홍길동");
        member.changeNickname("길동이");

        //when
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).orElseThrow();

        //then
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
    }
}