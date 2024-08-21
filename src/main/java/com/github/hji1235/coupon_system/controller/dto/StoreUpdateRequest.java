package com.github.hji1235.coupon_system.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreUpdateRequest {

    @NotEmpty
    @Size(min = 2, max = 50)
    private String name;
}
