package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class AlreadyUsedCouponException extends BusinessException{
    public AlreadyUsedCouponException(Long memberId) {
        super(ErrorCode.ALREADY_USED_COUPON, memberId);
    }
}
