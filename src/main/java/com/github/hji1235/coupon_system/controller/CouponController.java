package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.AdminCouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.StoreCouponSaveRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/admin/coupons")
    public ApiResponse<Void> adminCouponSave(@Valid @RequestBody AdminCouponSaveRequest adminCouponSaveRequest) {
        couponService.adminCouponSave(adminCouponSaveRequest);
        return ApiResponse.success();
    }

    @PostMapping("/stores/{storeId}/coupons")
    public ApiResponse<Void> storeCouponSave(
            @PathVariable Long storeId,
            @Valid @RequestBody StoreCouponSaveRequest storeCouponSaveRequest
    ) {
        couponService.storeCouponSave(storeId, storeCouponSaveRequest);
        return ApiResponse.success();
    }

}
