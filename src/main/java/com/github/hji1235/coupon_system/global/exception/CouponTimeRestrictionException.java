package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

import java.time.LocalTime;

public class CouponTimeRestrictionException extends BusinessException{
    public CouponTimeRestrictionException(LocalTime timeLimitStartAt, LocalTime timeLimitEndAt) {
        super(ErrorCode.COUPON_TIME_RESTRICTION, timeLimitStartAt, timeLimitEndAt);
    }
}
