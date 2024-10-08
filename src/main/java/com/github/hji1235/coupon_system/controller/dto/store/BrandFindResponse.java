package com.github.hji1235.coupon_system.controller.dto.store;

import com.github.hji1235.coupon_system.domain.store.Brand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class BrandFindResponse {

    private Long id;

    private String name;

    public BrandFindResponse(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }

    public BrandFindResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
