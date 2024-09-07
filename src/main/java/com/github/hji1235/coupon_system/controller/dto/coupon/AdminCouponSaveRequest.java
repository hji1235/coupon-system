package com.github.hji1235.coupon_system.controller.dto.coupon;

import com.github.hji1235.coupon_system.domain.coupon.IssuerType;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminCouponSaveRequest extends CouponSaveRequest {

    @Max(20000)
    @Min(5)
    private int discountAmount;

    @PositiveOrZero
    @Max(100000)
    private Integer minOrderPrice;

    @NotNull
    private IssuerType issuerType;

    @NotNull
    private Long issuerId;
}
