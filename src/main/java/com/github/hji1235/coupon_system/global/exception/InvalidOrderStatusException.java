package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.domain.order.OrderStatus;
import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class InvalidOrderStatusException extends BusinessException{
    public InvalidOrderStatusException(OrderStatus currentStatus, OrderStatus targetStatus) {
        super(ErrorCode.INVALID_ORDER_STATUS, currentStatus, targetStatus);
    }
}
