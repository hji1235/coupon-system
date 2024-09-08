package com.github.hji1235.coupon_system.controller.converter;

import com.github.hji1235.coupon_system.controller.dto.coupon.AdminCouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.CouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.StoreCouponDiscountDetailDto;
import com.github.hji1235.coupon_system.controller.dto.coupon.StoreCouponSaveRequest;
import com.github.hji1235.coupon_system.domain.coupon.*;
import org.springframework.stereotype.Component;

@Component
public class CouponConverter {

    public Coupon convertToAdminCoupon(AdminCouponSaveRequest dto) {
        ExpirationPolicy expirationPolicy = createExpirationPolicy(dto);
        TimeLimitPolicy timeLimitPolicy = createTimeLimitPolicy(dto);
        Long issuerId = determineIssuerId(dto);
        return Coupon.builder()
                .name(dto.getName())
                .discountAmount(dto.getDiscountAmount())
                .minOrderPrice(dto.getMinOrderPrice())
                .maxCount(dto.getMaxCount())
                .maxCountPerMember(dto.getMaxCountPerMember())
                .discountType(dto.getDiscountType())
                .issuerType(dto.getIssuerType())
                .issuerId(issuerId)
                .expirationPolicy(expirationPolicy)
                .timeLimitPolicy(timeLimitPolicy)
                .build();
    }

    public Coupon convertToStoreCoupon(Long storeId, StoreCouponSaveRequest dto, StoreCouponDiscountDetailDto discountDetailDto) {
        ExpirationPolicy expirationPolicy = createExpirationPolicy(dto);
        TimeLimitPolicy timeLimitPolicy = createTimeLimitPolicy(dto);
        return Coupon.builder()
                .name(dto.getName())
                .discountAmount(discountDetailDto.getDiscountAmount())
                .minOrderPrice(discountDetailDto.getMinOrderPrice())
                .maxCount(dto.getMaxCount())
                .maxCountPerMember(dto.getMaxCountPerMember())
                .discountType(dto.getDiscountType())
                .issuerType(IssuerType.STORE)
                .issuerId(storeId)
                .expirationPolicy(expirationPolicy)
                .timeLimitPolicy(timeLimitPolicy)
                .build();
    }

    private ExpirationPolicy createExpirationPolicy(CouponSaveRequest dto) {
        ExpirationPolicy expirationPolicy;
        if (dto.getExpirationPolicyType() == ExpirationPolicyType.PERIOD) {
            expirationPolicy = ExpirationPolicy.ofPeriod(dto.getStartAt(), dto.getExpiredAt());
        } else {
            expirationPolicy = ExpirationPolicy.ofAfterIssueDate(dto.getDaysFromIssuance());
        }
        return expirationPolicy;
    }

    private TimeLimitPolicy createTimeLimitPolicy(CouponSaveRequest dto) {
        TimeLimitPolicy timeLimitPolicy;
        if (dto.isTimeLimit()) {
            timeLimitPolicy = TimeLimitPolicy.ofTimeLimit(dto.getTimeLimitStartAt(), dto.getTimeLimitEndAt());
        } else {
            timeLimitPolicy = TimeLimitPolicy.ofNoTimeLimit();
        }
        return timeLimitPolicy;
    }

    private Long determineIssuerId(AdminCouponSaveRequest dto) {
        Long issuerId;
        if (dto.getIssuerType() == IssuerType.ADMIN) {
            issuerId = 0L;
        } else {
            issuerId = dto.getIssuerId();
        }
        return issuerId;
    }

}
