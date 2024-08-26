package com.github.hji1235.coupon_system.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenuDto {

    @NotNull
    private Long menuId;

    @NotNull
    private Integer quantity;
}
