package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.payment.PaymentSaveRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentService paymentService;

    /*
    결제 생성
     */
    @Secured("ROLE_MEMBER")
    @PostMapping("/orders/{orderId}/payments")
    public ApiResponse<Void> createPayment(
            @PathVariable Long orderId,
            @Valid @RequestBody PaymentSaveRequest paymentSaveRequest
            ) {
        paymentService.savePayment(orderId, paymentSaveRequest);
        return ApiResponse.success();
    }

    /*
    결제 완료
     */
    @Secured("ROLE_MEMBER")
    @PatchMapping("/payments/{paymentId}")
    public ApiResponse<Void> completePayment(@PathVariable Long paymentId) {
        paymentService.completePayment(paymentId);
        return ApiResponse.success();
    }

    /*
    결제 취소
     */
//    @DeleteMapping("/payments/{paymentId}")
//    public ApiResponse<Void> cancelPayment(@PathVariable Long paymentId) {
//        paymentService.cancelPayment(paymentId);
//        return ApiResponse.success();
//    }
}
