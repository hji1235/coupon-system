package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class PasswordMismatchException extends BusinessException{
    public PasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
