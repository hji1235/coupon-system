package com.github.hji1235.coupon_system.global.exception;

import com.github.hji1235.coupon_system.global.error.ErrorCode;

public class BrandNotFoundException extends EntityNotFoundException{
    public BrandNotFoundException(Long brandId) {
        super(String.valueOf(brandId), ErrorCode.BRAND_NOT_FOUND);
    }
}
