package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.CustomUserInfoDto;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.global.jwt.CustomUserDetails;
import com.github.hji1235.coupon_system.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음"));// 새로 만들기

        CustomUserInfoDto info = new CustomUserInfoDto(member);
        return new CustomUserDetails(info);
    }
}
