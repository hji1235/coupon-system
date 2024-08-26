package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class PaymentNotFoundException extends EntityNotFoundException{
    public PaymentNotFoundException(Long paymentId) {
        super(String.valueOf(paymentId), ErrorCode.PAYMENT_NOT_FOUND);
    }
}
