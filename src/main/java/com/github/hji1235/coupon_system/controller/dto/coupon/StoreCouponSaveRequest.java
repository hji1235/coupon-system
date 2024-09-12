package com.github.hji1235.coupon_system.controller.dto.coupon;

import com.github.hji1235.coupon_system.controller.dto.coupon.CouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.StoreCouponDiscountDetailDto;
import com.github.hji1235.coupon_system.controller.dto.coupon.annotation.ValidDiscountAmount;
import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ValidDiscountAmount(maxFixed = 5000)
public class StoreCouponSaveRequest extends CouponSaveRequest {

    @Valid
    @Size(min = 1, max = 3)
    List<StoreCouponDiscountDetailDto> discountDetails;

    @Builder
    public StoreCouponSaveRequest(String name, Integer maxCount, Integer maxCountPerMember,
                                  DiscountType discountType, ExpirationPolicyType expirationPolicyType,
                                  LocalDate startAt, LocalDate expiredAt, Integer daysFromIssuance,
                                  boolean timeLimit, LocalTime timeLimitStartAt, LocalTime timeLimitEndAt,
                                  List<StoreCouponDiscountDetailDto> discountDetails) {
        super(name, maxCount, maxCountPerMember, discountType, expirationPolicyType, startAt, expiredAt, daysFromIssuance, timeLimit, timeLimitStartAt, timeLimitEndAt);
        this.discountDetails = discountDetails;
    }
}
