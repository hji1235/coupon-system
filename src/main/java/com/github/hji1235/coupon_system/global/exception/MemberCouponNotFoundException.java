package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

import java.util.UUID;

public class MemberCouponNotFoundException extends EntityNotFoundException{
    public MemberCouponNotFoundException(Long memberCouponId) {
        super(String.valueOf(memberCouponId), ErrorCode.MEMBERCOUPON_NOT_FOUND);
    }

    public MemberCouponNotFoundException(UUID couponCode) {
        super(String.valueOf(couponCode), ErrorCode.MEMBERCOUPON_NOT_FOUND);
    }
}
