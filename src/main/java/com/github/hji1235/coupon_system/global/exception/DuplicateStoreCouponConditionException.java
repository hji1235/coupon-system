package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class DuplicateStoreCouponConditionException extends BusinessException{
    public DuplicateStoreCouponConditionException(int discountAmount, int minOrderPrice) {
        super(ErrorCode.DUPLICATE_STORE_COUPON_CONDITION, discountAmount, minOrderPrice);
    }
}
