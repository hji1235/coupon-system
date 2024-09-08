package com.github.hji1235.coupon_system.controller.dto.coupon;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCouponDiscountDetailDto {

    @Min(5)
    private int discountAmount;

    @PositiveOrZero
    @Max(100000)
    private Integer minOrderPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreCouponDiscountDetailDto that = (StoreCouponDiscountDetailDto) o;
        return getDiscountAmount() == that.getDiscountAmount() && Objects.equals(getMinOrderPrice(), that.getMinOrderPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDiscountAmount(), getMinOrderPrice());
    }
}
