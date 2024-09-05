package com.github.hji1235.coupon_system.domain.coupon;

import com.github.hji1235.coupon_system.domain.BaseEntity;
import com.github.hji1235.coupon_system.domain.order.Payment;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.domain.store.Store;
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

    @Column(name = "coupon_code")
    private UUID couponCode;

    @Column(name = "used", nullable = false)
    private Boolean used;

    @Column(name = "used_at")
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
        this.used = false;
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

    public void use() {
        // 쿠폰 사용 유효 체크 로직 (Exception 터트리는걸로)
        this.used = true;
        this.usedAt = LocalDateTime.now();
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
