package com.github.hji1235.coupon_system.controller.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreSaveRequest {

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotNull
    private Long brandId;

    public StoreSaveRequest(String name, Long brandId) {
        this.name = name;
        this.brandId = brandId;
    }
}
