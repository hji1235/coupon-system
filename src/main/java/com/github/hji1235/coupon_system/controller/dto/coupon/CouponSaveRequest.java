package com.github.hji1235.coupon_system.controller.dto.coupon;

import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicyType;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponSaveRequest {

    @NotBlank
    @Size(max = 30)
    private String name;

    @Max(10000000)
    @Min(1)
    private Integer maxCount;

    @Max(10)
    @Min(1)
    private Integer maxCountPerMember;

    @NotNull
    private DiscountType discountType;

    @NotNull
    private ExpirationPolicyType expirationPolicyType;

    private LocalDate startAt;

    @FutureOrPresent
    private LocalDate expiredAt;

    @Max(365)
    @Min(0)
    private Integer daysFromIssuance;

    private boolean timeLimit;

    private LocalTime timeLimitStartAt;

    private LocalTime timeLimitEndAt;
}
