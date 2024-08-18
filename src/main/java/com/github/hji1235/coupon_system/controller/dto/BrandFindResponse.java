package com.github.hji1235.coupon_system.controller.dto;

import com.github.hji1235.coupon_system.domain.store.Brand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrandFindResponse {

    private Long id;

    private String name;

    public BrandFindResponse(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }
}
