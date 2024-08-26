package com.github.hji1235.coupon_system.controller.dto;

import com.github.hji1235.coupon_system.domain.order.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderFindResponse {

    private String couponName;
    private int paymentAmount;
    private int discountAmount;

    public OrderFindResponse(Order order) {
        this.paymentAmount = order.getPayment().getPaymentAmount();
        this.discountAmount = order.getPayment().getDiscountAmount();
    }
}
