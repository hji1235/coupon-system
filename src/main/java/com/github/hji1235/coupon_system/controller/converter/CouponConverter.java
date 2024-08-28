package com.github.hji1235.coupon_system.controller.converter;

import com.github.hji1235.coupon_system.controller.dto.CouponSaveRequest;
import com.github.hji1235.coupon_system.domain.coupon.Coupon;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicy;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicyType;
import org.springframework.stereotype.Component;

@Component
public class CouponConverter {

    public Coupon convertToCoupon(CouponSaveRequest dto) {
        ExpirationPolicy expirationPolicy;
        if (dto.getExpirationPolicyType() == ExpirationPolicyType.PERIOD) {
            expirationPolicy = ExpirationPolicy.newByPeriod(dto.getStartAt(), dto.getExpiredAt());
        } else {
            expirationPolicy = ExpirationPolicy.newByAfterIssueDate(dto.getDaysFromIssuance());
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
                .build();
    }
}
