package com.github.hji1235.coupon_system.controller.dto.store;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuSaveRequest {

    @NotBlank
    @Size(max = 30)
    private String name;

    @PositiveOrZero
    private int price;

    public MenuSaveRequest(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
