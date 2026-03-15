package com.lhj.myoppingmall.auth.security;

import com.lhj.myoppingmall.global.exception.CustomException;
import com.lhj.myoppingmall.global.exception.ErrorCode;
import com.lhj.myoppingmall.member.entity.Member;
import com.lhj.myoppingmall.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다. (loginId : " + loginId + ")"));

        // 탈퇴 회원 체크
        if (member.isDeleted()) {
            throw new CustomException(ErrorCode.MEMBER_DELETED);
        }

        return new CustomUserDetails(member);
    }
}
