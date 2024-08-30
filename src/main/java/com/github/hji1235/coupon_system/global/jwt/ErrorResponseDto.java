package com.github.hji1235.coupon_system.global.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {

    private int status;

    private String message;

    private LocalDateTime serverTime;

    public ErrorResponseDto(int status, String message, LocalDateTime serverTime) {
        this.status = status;
        this.message = message;
        this.serverTime = serverTime;
    }
}
