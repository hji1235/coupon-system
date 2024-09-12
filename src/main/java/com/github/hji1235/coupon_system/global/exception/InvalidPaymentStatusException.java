package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.domain.order.PaymentStatus;
import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class InvalidPaymentStatusException extends BusinessException{
    public InvalidPaymentStatusException(PaymentStatus currentStatus, PaymentStatus targetStatus) {
        super(ErrorCode.INVALID_PAYMENT_STATUS, currentStatus, targetStatus);
    }
}
