package com.github.hji1235.coupon_system.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCouponCodeSaveRequest {

    private int quantity;
}
