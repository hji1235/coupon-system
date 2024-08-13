package com.github.hji1235.coupon_system.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpirationPolicy {

    @Enumerated(EnumType.STRING)
    @Column(name = "expiration_policy_type", nullable = false)
    private ExpirationPolicyType expirationPolicyType;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "days_from_issuance")
    private Integer daysFromIssuance;
}
