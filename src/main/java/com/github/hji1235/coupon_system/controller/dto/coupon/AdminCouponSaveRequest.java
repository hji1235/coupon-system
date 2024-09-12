package com.github.hji1235.coupon_system.controller.dto.coupon;

import com.github.hji1235.coupon_system.controller.dto.coupon.annotation.ValidDiscountAmount;
import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicyType;
import com.github.hji1235.coupon_system.domain.coupon.IssuerType;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ValidDiscountAmount(maxFixed = 20000)
public class AdminCouponSaveRequest extends CouponSaveRequest {

    @Min(5)
    private int discountAmount;

    @PositiveOrZero
    @Max(100000)
    private Integer minOrderPrice;

    @NotNull
    private IssuerType issuerType;

    @NotNull
    private Long issuerId;

    @Builder
    public AdminCouponSaveRequest(String name, Integer maxCount, Integer maxCountPerMember,
                                  DiscountType discountType, ExpirationPolicyType expirationPolicyType,
                                  LocalDate startAt, LocalDate expiredAt, Integer daysFromIssuance,
                                  boolean timeLimit, LocalTime timeLimitStartAt, LocalTime timeLimitEndAt,
                                  int discountAmount, Integer minOrderPrice, IssuerType issuerType, Long issuerId) {
        super(name, maxCount, maxCountPerMember, discountType, expirationPolicyType, startAt, expiredAt, daysFromIssuance, timeLimit, timeLimitStartAt, timeLimitEndAt);
        this.discountAmount = discountAmount;
        this.minOrderPrice = minOrderPrice;
        this.issuerType = issuerType;
        this.issuerId = issuerId;
    }
}
