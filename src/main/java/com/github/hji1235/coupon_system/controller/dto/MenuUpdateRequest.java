package com.github.hji1235.coupon_system.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuUpdateRequest {

    @NotEmpty
    @Size(min = 2, max = 50)
    private String name;

    @PositiveOrZero
    private Integer price;
}
