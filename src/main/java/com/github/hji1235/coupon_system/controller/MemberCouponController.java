package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberCouponController {

    private final MemberCouponService memberCouponService;

    @PostMapping("/members/{memberId}/coupons/{couponId}/issue")
    public ApiResponse<Void> memberCouponSave(
            @PathVariable Long memberId,
            @PathVariable Long couponId
    ) {
        memberCouponService.saveMemberCoupon(memberId, couponId);
        return ApiResponse.success();
    }
}
