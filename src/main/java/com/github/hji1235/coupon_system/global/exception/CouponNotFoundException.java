package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class CouponNotFoundException extends EntityNotFoundException{
    public CouponNotFoundException(Long couponId) {
        super(String.valueOf(couponId), ErrorCode.COUPON_NOT_FOUND);
    }
}
