package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class MemberCouponNotFoundException extends EntityNotFoundException{
    public MemberCouponNotFoundException(Long memberCouponId) {
        super(String.valueOf(memberCouponId), ErrorCode.MEMBERCOUPON_NOT_FOUND);
    }
}
