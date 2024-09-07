package com.github.hji1235.coupon_system.controller.dto.store;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuSaveRequest {

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @PositiveOrZero
    private Integer price;
}
