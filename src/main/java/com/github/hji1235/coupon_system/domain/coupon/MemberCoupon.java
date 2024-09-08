package com.github.hji1235.coupon_system.domain.coupon;

import com.github.hji1235.coupon_system.domain.BaseEntity;
import com.github.hji1235.coupon_system.domain.order.Payment;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.domain.store.Store;
import com.github.hji1235.coupon_system.global.exception.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id")
    private Long id;

    private UUID couponCode;

    private boolean used;

    private LocalDateTime usedAt;

    @Embedded
    private ExpirationPeriod expirationPeriod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private MemberCoupon(Coupon coupon, Member member) {
        this.coupon = coupon;
        this.member = member;
        this.couponCode = UUID.randomUUID();
        this.expirationPeriod = coupon.getExpirationPolicy().newExpirationPeriod();
    }

    public static MemberCoupon ofWithoutMember(Coupon coupon) {
        return new MemberCoupon(coupon, null);
    }

    public static MemberCoupon ofWithMember(Coupon coupon, Member member) {
        return new MemberCoupon(coupon, member);
    }

    public int getDiscountAmount() {
        return coupon.getDiscountAmount();
    }

    public void use(Store store, Integer paymentAmount) {
        validateCoupon(store, paymentAmount);
        this.used = true;
        this.usedAt = LocalDateTime.now();
    }

    private void validateCoupon(Store store, Integer paymentAmount) {
        Long issuerId = getIssuerIdForCheck(store);
        if (isUsed()) {
            throw new AlreadyUsedCouponException(this.id);
        }
        if (isExpired()) {
            throw new ExpiredCouponException(this.expirationPeriod.getExpiredAt());
        }
        if (isUnavailableTime()) {
            throw new CouponTimeRestrictionException(this.coupon.getTimeLimitPolicy().getTimeLimitStartAt(), this.coupon.getTimeLimitPolicy().getTimeLimitEndAt());
        }
        if (isIssuerMismatch(issuerId)) {
            throw new CouponIssuerMismatchException(this.coupon.getIssuerId(), issuerId);
        }
        if (isBelowMinOrderPrice(paymentAmount)) {
            throw new OrderPriceBelowMinimumException(paymentAmount, this.coupon.getMinOrderPrice());
        }
    }

    public boolean isAvailable(Store store, int paymentAmount) {
        Long issuerId = getIssuerIdForCheck(store);
        return !isIssuerMismatch(issuerId) && !isBelowMinOrderPrice(paymentAmount) && !isUnavailableTime();
    }

    private Long getIssuerIdForCheck(Store store) {
        if (coupon.isBrandCoupon()) {
            return store.getBrand().getId();
        }
        return store.getId();
    }

    public boolean isExpired() {
        return expirationPeriod.isExpired();
    }

    private boolean isIssuerMismatch(Long issuerId) {
        if (coupon.isAdminCoupon()) {
            return false;
        }
        return !coupon.getIssuerId().equals(issuerId);
    }

    private boolean isBelowMinOrderPrice(int paymentAmount) {
        if (coupon.getMinOrderPrice() == null) {
            return false;
        }
        return paymentAmount < coupon.getMinOrderPrice();
    }

    private boolean isUnavailableTime() {
        return coupon.getTimeLimitPolicy().isUnavailableTime();
    }

    private boolean isUsed() {
        return used;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void allocateMember(Member member) {
        this.member = member;
    }

    public boolean isAllocated() {
        return member != null;
    }
}
