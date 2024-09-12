package com.github.hji1235.coupon_system.controller.dto.coupon;

import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.MemberCoupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class MemberCouponFindResponse {

    private DiscountType discountType;

    private int discountAmount;

    private String couponName;

    private LocalDate expiredAt;

    private int minOrderPrice;

    public MemberCouponFindResponse(MemberCoupon memberCoupon) {
        this.discountType = memberCoupon.getCoupon().getDiscountType();
        this.discountAmount = memberCoupon.getDiscountAmount();
        this.couponName = memberCoupon.getCoupon().getName();
        this.expiredAt = memberCoupon.getExpirationPeriod().getExpiredAt();
        this.minOrderPrice = memberCoupon.getCoupon().getMinOrderPrice();
    }

    public MemberCouponFindResponse(DiscountType discountType, int discountAmount, String couponName, LocalDate expiredAt, int minOrderPrice) {
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.couponName = couponName;
        this.expiredAt = expiredAt;
        this.minOrderPrice = minOrderPrice;
    }
}
