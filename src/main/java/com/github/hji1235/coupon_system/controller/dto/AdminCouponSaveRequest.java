package com.github.hji1235.coupon_system.controller.dto;

import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicyType;
import com.github.hji1235.coupon_system.domain.coupon.IssuerType;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminCouponSaveRequest extends CouponSaveRequest{

    @NotNull
    @Max(10000)
    @Min(5)
    private Integer discountAmount;

    @PositiveOrZero
    @Max(100000)
    private Integer minOrderPrice;

    @NotNull
    private IssuerType issuerType;

    private Long issuerId;
}
