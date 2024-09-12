package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class DuplicateEmailException extends BusinessException{
    public DuplicateEmailException(String email) {
        super(ErrorCode.DUPLICATE_EMAIL, email);
    }
}
