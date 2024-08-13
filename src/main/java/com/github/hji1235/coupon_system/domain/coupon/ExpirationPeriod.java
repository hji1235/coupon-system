package com.github.hji1235.coupon_system.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpirationPeriod {

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;
}
