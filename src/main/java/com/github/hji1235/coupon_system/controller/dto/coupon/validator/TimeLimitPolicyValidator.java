package com.github.hji1235.coupon_system.controller.dto.coupon.validator;

import com.github.hji1235.coupon_system.controller.dto.coupon.CouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.annotation.ValidTimeLimitPolicy;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeLimitPolicyValidator implements ConstraintValidator<ValidTimeLimitPolicy, CouponSaveRequest> {
    @Override
    public boolean isValid(CouponSaveRequest request, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        if (request.isTimeLimit() && (request.getTimeLimitStartAt() == null || request.getTimeLimitEndAt() == null)) {
            valid = false;
        }
        if (!request.isTimeLimit() && (request.getTimeLimitStartAt() != null || request.getTimeLimitEndAt() != null)) {
            valid = false;
        }
        return valid;
    }
}
