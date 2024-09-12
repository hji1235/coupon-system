package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class DuplicateBrandNameException extends BusinessException{
    public DuplicateBrandNameException(String brandName) {
        super(ErrorCode.DUPLICATE_BRAND_NAME, brandName);
    }
}
