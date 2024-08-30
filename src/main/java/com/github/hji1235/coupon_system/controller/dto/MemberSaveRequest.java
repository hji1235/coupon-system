package com.github.hji1235.coupon_system.controller.dto;

import com.github.hji1235.coupon_system.domain.member.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSaveRequest {

    private String nickname;

    private String email;

    private String password;

    private Role role;
}
