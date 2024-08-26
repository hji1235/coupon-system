package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.PaymentSaveRequest;
import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.MemberCoupon;
import com.github.hji1235.coupon_system.domain.order.Order;
import com.github.hji1235.coupon_system.domain.order.Payment;
import com.github.hji1235.coupon_system.global.exception.MemberCouponNotFoundException;
import com.github.hji1235.coupon_system.global.exception.OrderNotFoundException;
import com.github.hji1235.coupon_system.global.exception.PaymentNotFoundException;
import com.github.hji1235.coupon_system.repository.MemberCouponRepository;
import com.github.hji1235.coupon_system.repository.OrderRepository;
import com.github.hji1235.coupon_system.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public void savePayment(Long orderId, PaymentSaveRequest paymentSaveRequest) {
        Order order = orderRepository.findOrder(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        int paymentAmount = order.calculateTotalPayment();
        int discountAmount = 0;
        if (paymentSaveRequest.getMemberCouponId() != null) {
            MemberCoupon memberCoupon = memberCouponRepository.findMemberCoupon(paymentSaveRequest.getMemberCouponId())
                    .orElseThrow(() -> new MemberCouponNotFoundException(paymentSaveRequest.getMemberCouponId()));
            discountAmount = memberCoupon.getDiscountAmount();
            if (memberCoupon.getCoupon().getDiscountType() == DiscountType.PERCENT) {
                discountAmount = paymentAmount / memberCoupon.getDiscountAmount();
            }
        }
        Payment payment = new Payment(paymentSaveRequest.getPaymentMethod(), paymentAmount, discountAmount, order);
        paymentRepository.save(payment);
    }

    @Transactional
    public void completePayment(Long paymentId) {
        Payment payment = paymentRepository.findPayment(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        payment.completePaymentAndPrepareOrder();
    }
}
