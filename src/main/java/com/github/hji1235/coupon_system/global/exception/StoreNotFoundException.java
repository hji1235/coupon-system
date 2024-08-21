package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class StoreNotFoundException extends EntityNotFoundException{
    public StoreNotFoundException(Long storeId) {
        super(String.valueOf(storeId), ErrorCode.STORE_NOT_FOUND);
    }
}
