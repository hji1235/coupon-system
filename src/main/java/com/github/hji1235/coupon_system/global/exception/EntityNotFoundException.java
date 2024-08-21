package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
