package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.CustomUserInfoDto;
import com.github.hji1235.coupon_system.controller.dto.member.MemberLoginRequest;
import com.github.hji1235.coupon_system.controller.dto.member.MemberSaveRequest;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.global.jwt.JwtUtil;
import com.github.hji1235.coupon_system.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public void join(MemberSaveRequest memberSaveRequest) {
        if (memberRepository.existsByEmail(memberSaveRequest.getEmail())) {
            throw new IllegalArgumentException("회원 이미 존재"); // 새로 만들기
        }
        Member member = new Member(memberSaveRequest.getEmail(), encoder.encode(memberSaveRequest.getPassword()), memberSaveRequest.getNickname(), memberSaveRequest.getRole());
        memberRepository.save(member);
    }

    @Transactional
    public String login(MemberLoginRequest memberLoginRequest) {
        String email = memberLoginRequest.getEmail();
        String password = memberLoginRequest.getPassword();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음")); // 새로 만들기
        if (!encoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치"); // 새로 만들기
        }
        CustomUserInfoDto info = new CustomUserInfoDto(member);
        return jwtUtil.createAccessToken(info);
    }
}
