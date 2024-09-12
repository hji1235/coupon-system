package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.payment.PaymentSaveRequest;
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
        Order order = findOrderByIdWithOrderMenus(orderId);
        int paymentAmount = order.calculateTotalPayment();
        Payment payment = Payment.of(paymentSaveRequest.getPaymentMethod(), paymentAmount, order);
        if (paymentSaveRequest.getMemberCouponId() != null) {
            MemberCoupon memberCoupon = findMemberCouponByIdWithCoupon(paymentSaveRequest.getMemberCouponId());
            payment.applyCoupon(memberCoupon);
        }
        paymentRepository.save(payment);
    }

    @Transactional
    public void completePayment(Long paymentId) {
        Payment payment = findPaymentByIdWithOrderAndMemberCoupon(paymentId);
        MemberCoupon memberCoupon = payment.getMemberCoupon();
        if (memberCoupon != null) {
            memberCoupon.use();
        }
        payment.updateStatusToCompleted();
    }

    @Transactional
    public void cancelPayment(Long paymentId) {
        Payment payment = findPaymentByIdWithOrderAndMemberCoupon(paymentId);
        if (payment.getMemberCoupon() != null) {
            payment.removeCoupon();
        }
        paymentRepository.delete(payment);
    }

    private Order findOrderByIdWithOrderMenus(Long orderId) {
        return orderRepository.findByIdWithOrderMenus(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private MemberCoupon findMemberCouponByIdWithCoupon(Long memberCouponId) {
        return memberCouponRepository.findByIdWithCoupon(memberCouponId)
                .orElseThrow(() -> new MemberCouponNotFoundException(memberCouponId));
    }

    private Payment findPaymentByIdWithOrderAndMemberCoupon(Long paymentId) {
        return paymentRepository.findByIdWithOrderAndMemberCoupon(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }
}
