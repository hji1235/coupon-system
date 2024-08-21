package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.CouponSaveRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private final CouponService couponService;

    @PostMapping("")
    public ApiResponse<Void> adminCouponSave(@Valid @RequestBody CouponSaveRequest couponSaveRequest) {
        couponService.couponSave(couponSaveRequest);
        return ApiResponse.success();
    }

//    @PostMapping("/stores/{storeId}")
//    public ApiResponse<Void> storeCouponSave(
//            @PathVariable Long storeId,
//            @Valid @RequestBody
//    ) {
//
//    }

}
