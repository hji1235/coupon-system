package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class MemberNotFoundException extends EntityNotFoundException{
    public MemberNotFoundException(Long memberId) {
        super(String.valueOf(memberId), ErrorCode.MEMBER_NOT_FOUND);
    }
}
