package com.github.hji1235.coupon_system.controller.dto.coupon;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCouponAllocateRequest {

    @NotNull
    private UUID couponCode;
}
