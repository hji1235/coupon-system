package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.CustomUserInfoDto;
import com.github.hji1235.coupon_system.controller.dto.member.MemberLoginRequest;
import com.github.hji1235.coupon_system.controller.dto.member.MemberSaveRequest;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.global.exception.DuplicateEmailException;
import com.github.hji1235.coupon_system.global.exception.DuplicateNicknameException;
import com.github.hji1235.coupon_system.global.exception.MemberNotFoundException;
import com.github.hji1235.coupon_system.global.exception.PasswordMismatchException;
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
        validateDuplicateEmail(memberSaveRequest.getEmail());
        validateDuplicateNickname(memberSaveRequest.getNickname());
        Member member = Member.of(
                memberSaveRequest.getEmail(),
                encoder.encode(memberSaveRequest.getPassword()),
                memberSaveRequest.getNickname(),
                memberSaveRequest.getRole());
        memberRepository.save(member);
    }

    @Transactional
    public String login(MemberLoginRequest memberLoginRequest) {
        Member member = findMemberByEmail(memberLoginRequest.getEmail());
        if (!encoder.matches(memberLoginRequest.getPassword(), member.getPassword())) {
            throw new PasswordMismatchException();
        }
        CustomUserInfoDto info = new CustomUserInfoDto(member);
        return jwtUtil.createAccessToken(info);
    }

    private void validateDuplicateNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException(nickname);
        }
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));
    }
}
