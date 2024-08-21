package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class CouponMaxCountOverException extends BusinessException{
    public CouponMaxCountOverException(int maxCount, int allocatedCount, int issuanceCount) {
        super(ErrorCode.COUPON_MAX_COUNT_OVER, maxCount, allocatedCount, issuanceCount);
    }
}
