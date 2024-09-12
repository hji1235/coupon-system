package com.github.hji1235.coupon_system.controller.dto.coupon;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCouponCodeSaveRequest {

    @Max(10000000)
    @Min(1)
    private int quantity;

    public MemberCouponCodeSaveRequest(int quantity) {
        this.quantity = quantity;
    }
}
