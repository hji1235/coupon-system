package com.github.hji1235.coupon_system.controller.dto;

import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicyType;
import com.github.hji1235.coupon_system.domain.coupon.IssuerType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCouponSaveRequest extends CouponSaveRequest{

    @Valid
    @Size(min = 1, max = 3)
    List<StoreCouponDiscountDetailDto> discountDetails;
}
