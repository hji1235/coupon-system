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
        Order order = findOrderByIdWithOrderMenus(orderId);
        int paymentAmount = order.calculateTotalPayment();
        int discountAmount = 0;
        MemberCoupon memberCoupon = null;
        if (paymentSaveRequest.getMemberCouponId() != null) {
            memberCoupon = findMemberCouponByIdWithCoupon(paymentSaveRequest.getMemberCouponId());
            discountAmount = calculateDiscountAmount(memberCoupon, paymentAmount);
        }
        Payment payment = Payment.of(paymentSaveRequest.getPaymentMethod(), paymentAmount, discountAmount, order);
        payment.setMemberCoupon(memberCoupon);
        paymentRepository.save(payment);
    }

    @Transactional
    public void completePayment(Long paymentId) {
        Payment payment = findPaymentByIdWIthOrderAndMemberCoupon(paymentId);
        MemberCoupon memberCoupon = payment.getMemberCoupon();
        if (memberCoupon != null) {
            memberCoupon.use(payment.getOrder().getStore(), payment.getPaymentAmount());
        }
        payment.completePaymentAndPendingOrder();
    }

    @Transactional
    public void cancelPayment(Long paymentId) {
        Payment payment = findPaymentByIdWIthOrderAndMemberCoupon(paymentId);
        payment.setMemberCoupon(null);
        paymentRepository.delete(payment);
    }

    private static int calculateDiscountAmount(MemberCoupon memberCoupon, int paymentAmount) {
        int discountAmount;
        discountAmount = memberCoupon.getDiscountAmount();
        if (memberCoupon.getCoupon().getDiscountType() == DiscountType.PERCENT) {
            discountAmount = paymentAmount / memberCoupon.getDiscountAmount();
        }
        return discountAmount;
    }

    private Order findOrderByIdWithOrderMenus(Long orderId) {
        return orderRepository.findByIdWithOrderMenus(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private MemberCoupon findMemberCouponByIdWithCoupon(Long memberCouponId) {
        return memberCouponRepository.findByIdWithCoupon(memberCouponId)
                .orElseThrow(() -> new MemberCouponNotFoundException(memberCouponId));
    }

    private Payment findPaymentByIdWIthOrderAndMemberCoupon(Long paymentId) {
        return paymentRepository.findByIdWithOrderAndMemberCoupon(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
    }
}
