package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class OrderNotFoundException extends EntityNotFoundException{
    public OrderNotFoundException(Long orderId) {
        super(String.valueOf(orderId), ErrorCode.ORDER_NOT_FOUND);
    }
}
