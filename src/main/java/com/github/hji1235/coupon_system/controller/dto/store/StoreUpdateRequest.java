package com.github.hji1235.coupon_system.controller.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreUpdateRequest {

    @NotBlank
    @Size(max = 30)
    private String name;

    public StoreUpdateRequest(String name) {
        this.name = name;
    }
}
