package com.github.hji1235.coupon_system.controller.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginRequest {

    @Email
    @Size(max = 254)
    private String email;

    @NotBlank
    private String password;
}
