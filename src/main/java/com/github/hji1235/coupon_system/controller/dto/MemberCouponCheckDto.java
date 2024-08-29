package com.github.hji1235.coupon_system.controller.dto;

import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.MemberCoupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCouponCheckDto {

    private DiscountType discountType;

    private int discountAmount;

    private String couponName;

    private LocalDateTime expiredAt;

    private int minOrderPrice;

    private boolean available;

    public MemberCouponCheckDto(MemberCoupon memberCoupon, Long issuerId, int paymentAmount) {
        this.discountType = memberCoupon.getCoupon().getDiscountType();
        this.discountAmount = memberCoupon.getDiscountAmount();
        this.couponName = memberCoupon.getCoupon().getName();
        this.expiredAt = memberCoupon.getExpirationPeriod().getExpiredAt();
        this.minOrderPrice = memberCoupon.getCoupon().getMinOrderPrice();
        this.available = memberCoupon.isAvailable(issuerId, paymentAmount);
    }
}
