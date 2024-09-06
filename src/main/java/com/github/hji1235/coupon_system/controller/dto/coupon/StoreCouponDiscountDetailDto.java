package com.github.hji1235.coupon_system.controller.dto.coupon;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCouponDiscountDetailDto {

    @NotNull
    @Max(10000)
    @Min(5)
    private Integer discountAmount;

    @PositiveOrZero
    @Max(100000)
    private Integer minOrderPrice;
}
