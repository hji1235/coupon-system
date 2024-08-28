package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.PaymentSaveRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/orders/{orderId}/payments")
    public ApiResponse<Void> paymentSave(
            @PathVariable Long orderId,
            @Valid @RequestBody PaymentSaveRequest paymentSaveRequest
            ) {
        paymentService.savePayment(orderId, paymentSaveRequest);
        return ApiResponse.success();
    }

    @PatchMapping("/payments/{paymentId}")
    public ApiResponse<Void> paymentComplete(@PathVariable Long paymentId) {
        paymentService.completePayment(paymentId);
        return ApiResponse.success();
    }

    @DeleteMapping("/payments/{paymentId}")
    public ApiResponse<Void> paymentCancel(@PathVariable Long paymentId) {
        paymentService.cancelPayment(paymentId);
        return ApiResponse.success();
    }

}
