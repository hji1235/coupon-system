package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorCode) {
        super(errorCode.getMessage() + " " + message);
        this.errorCode = errorCode;
    }
}
