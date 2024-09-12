package com.github.hji1235.coupon_system.controller.dto.coupon.annotation;

import com.github.hji1235.coupon_system.controller.dto.coupon.validator.DiscountAmountValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DiscountAmountValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDiscountAmount {
    String message() default "유효하지 않은 할인 금액입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int minFixed() default 1000;
    int maxFixed() default 20000;
    int minPercent() default 5;
    int maxPercent() default 100;
}
