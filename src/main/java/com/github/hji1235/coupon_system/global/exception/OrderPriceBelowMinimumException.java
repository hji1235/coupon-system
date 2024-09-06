package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class OrderPriceBelowMinimumException extends BusinessException{
    public OrderPriceBelowMinimumException(Integer currentOrderPrice, Integer couponMinOrderPrice) {
        super(ErrorCode.ORDER_PRICE_BELOW_MINIMUM, currentOrderPrice, couponMinOrderPrice);
    }
}
