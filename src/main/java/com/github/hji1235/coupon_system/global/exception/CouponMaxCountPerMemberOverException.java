package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class CouponMaxCountPerMemberOverException extends BusinessException{
    public CouponMaxCountPerMemberOverException(int maxCountPerMember, int currentCount) {
        super(ErrorCode.COUPON_MAX_COUNT_FOR_MEMBER_OVER, maxCountPerMember, currentCount);
    }
}
