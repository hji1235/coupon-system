package com.github.hji1235.coupon_system.domain.order;

import com.github.hji1235.coupon_system.domain.BaseEntity;
import com.github.hji1235.coupon_system.domain.coupon.MemberCoupon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_amount", nullable = false)
    private Integer paymentAmount;

//    @Column(name = "payment_at")
//    private LocalDateTime paymentAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "discount_amount")
    private Integer discountAmount;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "payment")
    private MemberCoupon memberCoupon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public Payment(PaymentMethod paymentMethod, Integer paymentAmount, Integer discountAmount, Order order) {
        this.paymentMethod = paymentMethod;
        this.paymentAmount = paymentAmount;
        this.paymentStatus = PaymentStatus.PENDING;
        this.discountAmount = discountAmount;
        this.order = order;
    }

    public void completePaymentAndPrepareOrder() {
        if (paymentStatus != PaymentStatus.COMPLETED) {
            paymentStatus = PaymentStatus.COMPLETED;
        }
        if (order != null && order.getOrderStatus() != OrderStatus.PREPARING) {
            order.prepareOrder();
        }
    }

    public void setMemberCoupon(MemberCoupon memberCoupon) {
        if (memberCoupon == null) {
            if (this.memberCoupon != null) {
                this.memberCoupon.setPayment(null);
            }
        } else {
            memberCoupon.setPayment(this);
        }
        this.memberCoupon = memberCoupon;
    }
}
