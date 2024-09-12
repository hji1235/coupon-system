package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class DuplicateStoreNameException extends BusinessException{
    public DuplicateStoreNameException(String storeName) {
        super(ErrorCode.DUPLICATE_STORE_NAME, storeName);
    }
}
