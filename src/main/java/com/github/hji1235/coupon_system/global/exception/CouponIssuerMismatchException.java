package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class CouponIssuerMismatchException extends BusinessException{
    public CouponIssuerMismatchException(Long currentStoreId, Long memberCouponIssuerId) {
        super(ErrorCode.COUPON_ISSUER_MISMATCH, currentStoreId, memberCouponIssuerId);
    }
}
