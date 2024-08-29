package com.github.hji1235.coupon_system.controller.converter;

import com.github.hji1235.coupon_system.controller.dto.AdminCouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.StoreCouponSaveRequest;
import com.github.hji1235.coupon_system.domain.coupon.Coupon;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicy;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicyType;
import com.github.hji1235.coupon_system.domain.coupon.IssuerType;
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

    public Coupon convertToStoreCoupon(Long storeId, StoreCouponSaveRequest dto) {
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
                .issuerType(IssuerType.STORE)
                .issuerId(storeId)
                .expirationPolicy(expirationPolicy)
                .build();
    }

}
