package com.github.hji1235.coupon_system.controller.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenuDto {

    @NotNull
    private Long menuId;

    @Min(1)
    private int quantity;
}
