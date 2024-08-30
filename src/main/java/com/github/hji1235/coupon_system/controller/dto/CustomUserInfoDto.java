package com.github.hji1235.coupon_system.controller.dto;

import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.domain.member.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomUserInfoDto {

    private Long id;

    private String email;

    private String password;

    private String nickname;

    private Role role;

    public CustomUserInfoDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.role = member.getRole();
    }
}
