package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExpiredCouponException extends BusinessException{
    public ExpiredCouponException(LocalDate expiredAt) {
        super(ErrorCode.EXPIRED_COUPON, String.valueOf(expiredAt));
    }
}
