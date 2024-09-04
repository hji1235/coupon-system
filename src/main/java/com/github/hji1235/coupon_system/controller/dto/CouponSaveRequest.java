package com.github.hji1235.coupon_system.controller.dto;

import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicyType;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponSaveRequest {

    @NotEmpty
    @Size(min = 2, max = 50)
    private String name;

    @Max(1000000)
    @Min(1)
    private Integer maxCount;

    @Max(10)
    @Min(1)
    private Integer maxCountPerMember;

    @NotNull
    private DiscountType discountType;

    @NotNull
    private ExpirationPolicyType expirationPolicyType;

    private LocalDateTime startAt;

    @FutureOrPresent
    private LocalDateTime expiredAt;

    @Max(365)
    @Min(0)
    private Integer daysFromIssuance;

    private boolean timeLimit;

    private LocalTime timeLimitStartAt;

    private LocalTime timeLimitEndAt;
}
