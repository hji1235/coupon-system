package com.github.hji1235.coupon_system.controller.converter;

import com.github.hji1235.coupon_system.controller.dto.AdminCouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.StoreCouponSaveRequest;
import com.github.hji1235.coupon_system.domain.coupon.*;
import org.springframework.stereotype.Component;

@Component
public class CouponConverter {

    public Coupon convertToAdminCoupon(AdminCouponSaveRequest dto) {
        ExpirationPolicy expirationPolicy;
        if (dto.getExpirationPolicyType() == ExpirationPolicyType.PERIOD) {
            expirationPolicy = ExpirationPolicy.newByPeriod(dto.getStartAt(), dto.getExpiredAt());
        } else {
            expirationPolicy = ExpirationPolicy.newByAfterIssueDate(dto.getDaysFromIssuance());
        }
        TimeLimitPolicy timeLimitPolicy;
        if (dto.isTimeLimit()) {
            timeLimitPolicy = TimeLimitPolicy.newTimeLimitPolicy(dto.getTimeLimitStartAt(), dto.getTimeLimitEndAt());
        } else {
            timeLimitPolicy = TimeLimitPolicy.newTimeLimitPolicy();
        }
        return Coupon.builder()
                .name(dto.getName())
                .discountAmount(dto.getDiscountAmount())
                .minOrderPrice(dto.getMinOrderPrice())
                .maxCount(dto.getMaxCount())
                .maxCountPerMember(dto.getMaxCountPerMember())
                .allocatedCount(0)
                .discountType(dto.getDiscountType())
                .issuerType(dto.getIssuerType())
                .issuerId(dto.getIssuerId())
                .expirationPolicy(expirationPolicy)
                .timeLimitPolicy(timeLimitPolicy)
                .build();
    }

    public Coupon convertToStoreCoupon(Long storeId, StoreCouponSaveRequest dto) {
        ExpirationPolicy expirationPolicy;
        if (dto.getExpirationPolicyType() == ExpirationPolicyType.PERIOD) {
            expirationPolicy = ExpirationPolicy.newByPeriod(dto.getStartAt(), dto.getExpiredAt());
        } else {
            expirationPolicy = ExpirationPolicy.newByAfterIssueDate(dto.getDaysFromIssuance());
        }
        TimeLimitPolicy timeLimitPolicy;
        if (dto.isTimeLimit()) {
            timeLimitPolicy = TimeLimitPolicy.newTimeLimitPolicy(dto.getTimeLimitStartAt(), dto.getTimeLimitEndAt());
        } else {
            timeLimitPolicy = TimeLimitPolicy.newTimeLimitPolicy();
        }
        return Coupon.builder()
                .name(dto.getName())
                .discountAmount(dto.getDiscountAmount())
                .minOrderPrice(dto.getMinOrderPrice())
                .maxCount(dto.getMaxCount())
                .maxCountPerMember(dto.getMaxCountPerMember())
                .allocatedCount(0)
                .discountType(dto.getDiscountType())
                .issuerType(IssuerType.STORE)
                .issuerId(storeId)
                .expirationPolicy(expirationPolicy)
                .timeLimitPolicy(timeLimitPolicy)
                .build();
    }

}
