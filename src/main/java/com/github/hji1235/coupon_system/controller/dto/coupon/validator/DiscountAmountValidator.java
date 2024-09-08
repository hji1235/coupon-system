package com.github.hji1235.coupon_system.controller.dto.coupon.validator;

import com.github.hji1235.coupon_system.controller.dto.coupon.AdminCouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.StoreCouponDiscountDetailDto;
import com.github.hji1235.coupon_system.controller.dto.coupon.StoreCouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.annotation.ValidDiscountAmount;
import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DiscountAmountValidator implements ConstraintValidator<ValidDiscountAmount, Object> {

    private int minFixed;
    private int maxFixed;
    private int minPercent;
    private int maxPercent;
    @Override
    public void initialize(ValidDiscountAmount constraintAnnotation) {
        this.minFixed = constraintAnnotation.minFixed();
        this.maxFixed = constraintAnnotation.maxFixed();
        this.minPercent = constraintAnnotation.minPercent();
        this.maxPercent = constraintAnnotation.maxPercent();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof AdminCouponSaveRequest request) {
            return validateDiscountAmount(request.getDiscountType(), request.getDiscountAmount(), context);
        } else if (obj instanceof StoreCouponSaveRequest request) {
            for (StoreCouponDiscountDetailDto dto : request.getDiscountDetails()) {
                if (!validateDiscountAmount(request.getDiscountType(), dto.getDiscountAmount(), context)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean validateDiscountAmount(DiscountType discountType, int discountAmount, ConstraintValidatorContext context) {
        if (discountType == DiscountType.FIXED && (discountAmount < minFixed || discountAmount > maxFixed)) {
            addConstraintViolation(context, String.format("고정 금액 할인 금액이 올바르지 않습니다. %d <= discountAmount <= %d", minFixed, maxFixed));
            return false;
        }
        if (discountType == DiscountType.PERCENT && (discountAmount < minPercent || discountAmount > maxPercent)) {
            addConstraintViolation(context, String.format("퍼센트 할인 금액이 올바르지 않습니다. %d <= discountAmount <= %d", minPercent, maxPercent));
            return false;
        }
        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
