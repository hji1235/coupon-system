package com.github.hji1235.coupon_system.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpirationPeriod {

    @Column(nullable = false)
    private LocalDate startAt;

    @Column(nullable = false)
    private LocalDate expiredAt;

    private ExpirationPeriod(LocalDate startAt, LocalDate expiredAt) {
        this.startAt = startAt;
        this.expiredAt = expiredAt;
    }

    public static ExpirationPeriod ofAfterIssueDate(Integer daysFromIssuance) {
        LocalDate now = LocalDate.now();
        return new ExpirationPeriod(now, now.plusDays(daysFromIssuance));
    }

    public static ExpirationPeriod ofPeriod(LocalDate startAt, LocalDate expiredAt) {
        return new ExpirationPeriod(startAt, expiredAt);
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiredAt);
    }
}
