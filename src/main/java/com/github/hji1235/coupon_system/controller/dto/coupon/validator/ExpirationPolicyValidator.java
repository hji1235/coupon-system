package com.github.hji1235.coupon_system.controller.dto.coupon.validator;

import com.github.hji1235.coupon_system.controller.dto.coupon.CouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.annotation.ValidExpirationPolicy;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicyType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExpirationPolicyValidator implements ConstraintValidator<ValidExpirationPolicy, CouponSaveRequest> {
    @Override
    public boolean isValid(CouponSaveRequest request, ConstraintValidatorContext context) {
        boolean valid = true;
        if (request.getExpirationPolicyType() == ExpirationPolicyType.PERIOD && (request.getStartAt() == null || request.getExpiredAt() == null || request.getDaysFromIssuance() != null)) {
            valid = false;
        }
        if (request.getExpirationPolicyType() == ExpirationPolicyType.AFTER_ISSUE_DATE && (request.getStartAt() != null || request.getExpiredAt() != null || request.getDaysFromIssuance() == null)) {
            valid = false;
        }
        return valid;
    }
}
