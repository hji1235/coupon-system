package com.github.hji1235.coupon_system.controller.dto.payment;

import com.github.hji1235.coupon_system.domain.order.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentSaveRequest {

    @NotNull
    private PaymentMethod paymentMethod;

    private Long memberCouponId;
}
