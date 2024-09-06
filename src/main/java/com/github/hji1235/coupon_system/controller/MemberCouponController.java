package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.coupon.MemberCouponAllocateRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.MemberCouponAvailableCheckResponse;
import com.github.hji1235.coupon_system.controller.dto.coupon.MemberCouponCodeSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.MemberCouponFindResponse;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.global.jwt.CustomUserDetails;
import com.github.hji1235.coupon_system.service.MemberCouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PostMapping("/coupons/{couponId}/member-coupons/issue-code")
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
    @PostMapping("/coupons/{couponId}/member-coupons/allocate-click")
    public ApiResponse<Void> allocateMemberCouponByClick(
            @PathVariable Long couponId,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        Long memberId = userDetails.getMemberId();
        memberCouponService.saveAndAllocateMemberCouponByClick(memberId, couponId);
        return ApiResponse.success();
    }

    /*
    쿠폰 할당(쿠폰 코드)
     */
    @PatchMapping("/member-coupons/allocate-code")
    public ApiResponse<Void> allocateMemberCouponByCode(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody MemberCouponAllocateRequest memberCouponAllocateRequest
    ) {
        Long memberId = userDetails.getMemberId();
        memberCouponService.allocateMemberCouponByCode(memberId, memberCouponAllocateRequest);
        return ApiResponse.success();
    }

    /*
    멤버 쿠폰 조회
     */
    @GetMapping("/member-coupons")
    public ApiResponse<List<MemberCouponFindResponse>> getAllMemberCoupons(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        List<MemberCouponFindResponse> memberCouponFindResponses = memberCouponService.findAllMemberCoupons(memberId);
        return ApiResponse.success(memberCouponFindResponses);
    }

    /*
    주문 중 사용 가능 쿠폰 조회
     */
    @GetMapping("/stores/{storeId}/orders/{orderId}/member-coupons/check")
    public ApiResponse<List<MemberCouponAvailableCheckResponse>> getAllAvailableMemberCoupons(
            @PathVariable Long storeId,
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long memberId = userDetails.getMemberId();
        List<MemberCouponAvailableCheckResponse> memberCouponAvailableCheckResponses = memberCouponService.availableCheckMemberCoupons(memberId, storeId, orderId);
        return ApiResponse.success(memberCouponAvailableCheckResponses);
    }
}
