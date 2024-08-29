package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.MemberCouponCheckResponse;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.MemberCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/members/{memberId}/stores/{storeId}/orders/{orderId}/member-coupons/check")
    public ApiResponse<MemberCouponCheckResponse> memberCouponCheck(
            @PathVariable Long memberId,
            @PathVariable Long storeId,
            @PathVariable Long orderId
    ) {
        MemberCouponCheckResponse memberCouponCheckResponse = memberCouponService.checkMemberCoupons(memberId, storeId, orderId);
        return ApiResponse.success(memberCouponCheckResponse);
    }
}
