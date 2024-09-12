package com.github.hji1235.coupon_system.domain.order;

import com.github.hji1235.coupon_system.domain.BaseEntity;
import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.MemberCoupon;
import com.github.hji1235.coupon_system.global.exception.InvalidPaymentStatusException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    private int paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private int discountAmount;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private MemberCoupon memberCoupon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Payment(PaymentMethod paymentMethod, int paymentAmount, Order order) {
        this.paymentMethod = paymentMethod;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = PaymentStatus.PENDING;
        this.order = order;
    }

    public static Payment of(PaymentMethod paymentMethod, int paymentAmount, Order order) {
        return new Payment(paymentMethod, paymentAmount, order);
    }

    public void updateStatusToCompleted() {
        if (paymentStatus != PaymentStatus.PENDING) {
            throw new InvalidPaymentStatusException(this.paymentStatus, PaymentStatus.COMPLETED);
        }
        paymentStatus = PaymentStatus.COMPLETED;
        order.updateStatusToPending();
    }

    public void applyCoupon(MemberCoupon memberCoupon) {
        this.discountAmount = calculateDiscountAmount(memberCoupon, this.paymentAmount);
        this.memberCoupon = memberCoupon;
        memberCoupon.setPayment(this);
    }

    public void removeCoupon() {
        this.memberCoupon.setPayment(null);
        this.memberCoupon = null;
    }

    private static int calculateDiscountAmount(MemberCoupon memberCoupon, int paymentAmount) {
        if (memberCoupon.getCoupon().getDiscountType() == DiscountType.PERCENT) {
            return (int) (paymentAmount * (memberCoupon.getDiscountAmount() / 100.0));
        }
        return memberCoupon.getDiscountAmount();
    }
}
