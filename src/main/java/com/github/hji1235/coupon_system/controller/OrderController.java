package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.order.OrderFindResponse;
import com.github.hji1235.coupon_system.controller.dto.order.OrderSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.order.SimpleOrderResponse;
import com.github.hji1235.coupon_system.controller.dto.order.StoreOrderFindResponse;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.global.jwt.CustomUserDetails;
import com.github.hji1235.coupon_system.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    /*
    주문 생성
     */
    @Secured("ROLE_MEMBER")
    @PostMapping("/stores/{storeId}/orders")
    public ApiResponse<Void> createOrder(
            @PathVariable Long storeId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody OrderSaveRequest orderSaveRequest
    ) {
        Long memberId = userDetails.getMemberId();
        orderService.saveOrder(storeId, memberId, orderSaveRequest);
        return ApiResponse.success();
    }

    /*
    주문 상세 조회
     */
    @Secured("ROLE_MEMBER")
    @GetMapping("/orders/{orderId}")
    public ApiResponse<OrderFindResponse> getOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long memberId = userDetails.getMemberId();
        OrderFindResponse orderFindResponse = orderService.findOrder(orderId, memberId);
        return ApiResponse.success(orderFindResponse);
    }

    /*
    주문 리스트 조회
     */
    @Secured("ROLE_MEMBER")
    @GetMapping("/orders")
    public ApiResponse<List<SimpleOrderResponse>> getAllOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        List<SimpleOrderResponse> simpleOrderResponses = orderService.findAllOrders(memberId);
        return ApiResponse.success(simpleOrderResponses);
    }

    /*
    가게의 주문 리스트 조회
     */
    @Secured("ROLE_STORE")
    @GetMapping("/stores/{storeId}/orders")
    public ApiResponse<List<StoreOrderFindResponse>> getAllStoreOrders(@PathVariable Long storeId) {
        List<StoreOrderFindResponse> storeOrderFindResponses = orderService.findAllStoreOrders(storeId);
        return ApiResponse.success(storeOrderFindResponses);
    }

    /*
    가게의 주문 수락
     */
    @Secured("ROLE_STORE")
    @PatchMapping("/orders/{orderId}/accept")
    public ApiResponse<Void> updateOrderStatusToPreparing(@PathVariable Long orderId) {
        orderService.updateOrderStatusToPreparing(orderId);
        return ApiResponse.success();
    }

    /*
    가게의 주문 완료
     */
    @Secured("ROLE_STORE")
    @PatchMapping("/orders/{orderId}/complete")
    public ApiResponse<Void> updateOrderStatusToComplete(@PathVariable Long orderId) {
        orderService.updateOrderStatusToComplete(orderId);
        return ApiResponse.success();
    }
}
