package com.github.hji1235.coupon_system.controller.dto.coupon.annotation;

import com.github.hji1235.coupon_system.controller.dto.coupon.validator.ExpirationPolicyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExpirationPolicyValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidExpirationPolicy {
    String message() default "유효 기간 설정값이 올바르지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
