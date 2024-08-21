package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class MenuNotFoundException extends EntityNotFoundException{
    public MenuNotFoundException(Long menuId) {
        super(String.valueOf(menuId), ErrorCode.MENU_NOT_FOUND);
    }
}
