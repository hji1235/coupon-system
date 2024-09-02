package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class InvalidMenuAccessException extends BusinessException{
    public InvalidMenuAccessException(Long storeId, Long menuId) {
        super(ErrorCode.INVALID_MENU_ACCESS, storeId, menuId);
    }
}
