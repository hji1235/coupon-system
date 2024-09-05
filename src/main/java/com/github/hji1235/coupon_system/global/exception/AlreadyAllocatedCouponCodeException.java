package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

import java.util.UUID;

public class AlreadyAllocatedCouponCodeException extends BusinessException{
    public AlreadyAllocatedCouponCodeException(UUID couponCode) {
        super(ErrorCode.ALREADY_ALLOCATED_COUPON_CODE, String.valueOf(couponCode));
    }
}
