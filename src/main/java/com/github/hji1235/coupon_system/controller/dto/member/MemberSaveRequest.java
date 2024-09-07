package com.github.hji1235.coupon_system.controller.dto.member;

import com.github.hji1235.coupon_system.domain.member.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSaveRequest {

    @NotBlank
    @Size(min = 2, max = 15)
    private String nickname;

    @Email
    @NotBlank
    @Size(max = 254)
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    private String password;

    @NotNull
    private Role role;
}
