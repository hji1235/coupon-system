package com.github.hji1235.coupon_system.domain.coupon;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimeLimitPolicy {

    private boolean timeLimit;

    private LocalTime timeLimitStartAt;

    private LocalTime timeLimitEndAt;

    private TimeLimitPolicy(boolean timeLimit, LocalTime timeLimitStartAt, LocalTime timeLimitEndAt) {
        this.timeLimit = timeLimit;
        this.timeLimitStartAt = timeLimitStartAt;
        this.timeLimitEndAt = timeLimitEndAt;
    }

    public static TimeLimitPolicy ofTimeLimit(LocalTime timeLimitStartAt, LocalTime timeLimitEndAt) {
        return new TimeLimitPolicy(true, timeLimitStartAt, timeLimitEndAt);
    }

    public static TimeLimitPolicy ofNoTimeLimit() {
        return new TimeLimitPolicy(false, null, null);
    }

    public boolean isUnavailableTime() {
        LocalTime currentTime = LocalTime.now();
        return timeLimit && (currentTime.isBefore(timeLimitStartAt) || currentTime.isAfter(timeLimitEndAt));
    }
}
