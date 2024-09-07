package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class DuplicateMenuNameInStoreException extends BusinessException{
    public DuplicateMenuNameInStoreException(String menuName) {
        super(ErrorCode.DUPLICATE_MENU_NAME_IN_STORE, menuName);
    }
}
