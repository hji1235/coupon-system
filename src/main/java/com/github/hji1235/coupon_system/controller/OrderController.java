package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.OrderFindAllResponse;
import com.github.hji1235.coupon_system.controller.dto.OrderFindResponse;
import com.github.hji1235.coupon_system.controller.dto.OrderSaveRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders/members/{memberId}")
    public ApiResponse<Void> orderSave(
            @PathVariable Long memberId,
            @Valid @RequestBody OrderSaveRequest orderSaveRequest
    ) {
        orderService.saveOrder(memberId, orderSaveRequest);
        return ApiResponse.success();
    }

    @GetMapping("/orders/members/{memberId}")
    public ApiResponse<OrderFindAllResponse> orderFindAll(@PathVariable Long memberId) {
        OrderFindAllResponse orderFindAllResponse = orderService.findAllOrders(memberId);
        return ApiResponse.success(orderFindAllResponse);
    }

    /*
    주문 상세 조회는 멤버쿠폰을 사용하여 결제와 연관관계가 생겼을 시점에 만들기(현재 서로 연관 관계가 없어서 조회가 불가능)
     */
    @GetMapping("/orders/{orderId}")
    public ApiResponse<OrderFindResponse> orderDetails(
            @PathVariable Long orderId
    ) {
        OrderFindResponse orderFindResponse = orderService.findOrder(orderId);
        return ApiResponse.success(orderFindResponse);
    }
}
