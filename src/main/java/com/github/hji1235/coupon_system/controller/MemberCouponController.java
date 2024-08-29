package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.MemberCouponAllocateRequest;
import com.github.hji1235.coupon_system.controller.dto.MemberCouponCheckResponse;
import com.github.hji1235.coupon_system.controller.dto.MemberCouponCodeSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.MemberCouponFindAllResponse;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.MemberCouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberCouponController {

    private final MemberCouponService memberCouponService;

    /*
    미할당 쿠폰 생성
     */
    @PostMapping("/coupons/{couponId}/issue-code")
    public ApiResponse<Void> issueCouponCodes(
            @PathVariable Long couponId,
            @Valid @RequestBody MemberCouponCodeSaveRequest memberCouponCodeSaveRequest
    ) {
        memberCouponService.saveMemberCouponCodes(couponId, memberCouponCodeSaveRequest);
        return ApiResponse.success();
    }

    /*
    쿠폰 할당(클릭)
     */
    @PostMapping("/members/{memberId}/coupons/{couponId}/issue")
    public ApiResponse<Void> memberCouponSave(
            @PathVariable Long memberId,
            @PathVariable Long couponId
    ) {
        memberCouponService.saveMemberCoupon(memberId, couponId);
        return ApiResponse.success();
    }

    /*
    쿠폰 할당(쿠폰 코드)
     */
    @PatchMapping("/members/{memberId}/member-coupons")
    public ApiResponse<Void> memberCouponAllocate(
            @PathVariable Long memberId,
            @Valid @RequestBody MemberCouponAllocateRequest memberCouponAllocateRequest
    ) {
        memberCouponService.allocateMemberCoupon(memberId, memberCouponAllocateRequest);
        return ApiResponse.success();
    }

    @GetMapping("/members/{memberId}/member-coupons")
    public ApiResponse<List<MemberCouponFindAllResponse>> memberCouponFindAll(@PathVariable Long memberId) {
        List<MemberCouponFindAllResponse> memberCoupons = memberCouponService.findAllMemberCoupons(memberId);
        return ApiResponse.success(memberCoupons);
    }

    /*
    주문 중 사용 가능 쿠폰 조회
     */
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
