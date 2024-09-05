package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class MenuNotBelongToStoreException extends BusinessException{
    public MenuNotBelongToStoreException(Long menuId, Long storeId) {
        super(ErrorCode.MENU_NOT_BELONG_TO_STORE, menuId, storeId);
    }
}
