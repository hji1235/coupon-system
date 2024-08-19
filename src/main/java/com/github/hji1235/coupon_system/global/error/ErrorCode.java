package com.github.hji1235.coupon_system.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    BRAND_NOT_FOUND(400, "TEST1", "Can not find a Brand for id"),
    STORE_NOT_FOUND(400, "TEST2", "Can not find a Brand for id");


    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
