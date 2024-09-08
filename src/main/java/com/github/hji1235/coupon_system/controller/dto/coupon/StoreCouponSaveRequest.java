package com.github.hji1235.coupon_system.controller.dto.coupon;

import com.github.hji1235.coupon_system.controller.dto.coupon.CouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.StoreCouponDiscountDetailDto;
import com.github.hji1235.coupon_system.controller.dto.coupon.annotation.ValidDiscountAmount;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ValidDiscountAmount(maxFixed = 5000)
public class StoreCouponSaveRequest extends CouponSaveRequest {

    @Valid
    @Size(min = 1, max = 3)
    List<StoreCouponDiscountDetailDto> discountDetails;
}
