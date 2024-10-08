package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.coupon.AdminCouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.StoreCouponSaveRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CouponController {

    private final CouponService couponService;

    /*
    어드민 쿠폰 생성(어드민 쿠폰, 브랜드 쿠폰)
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/coupons")
    public ApiResponse<Long> createAdminCoupon(@Valid @RequestBody AdminCouponSaveRequest adminCouponSaveRequest) {
        Long savedCouponId = couponService.adminCouponSave(adminCouponSaveRequest);
        return ApiResponse.success(savedCouponId);
    }

    /*
    스토어 쿠폰 생성(스토어 쿠폰)
     */
    @Secured("ROLE_STORE")
    @PostMapping("/stores/{storeId}/coupons")
    public ApiResponse<Void> createStoreCoupons(
            @PathVariable Long storeId,
            @Valid @RequestBody StoreCouponSaveRequest storeCouponSaveRequest
    ) {
        couponService.storeCouponSave(storeId, storeCouponSaveRequest);
        return ApiResponse.success();
    }

}
