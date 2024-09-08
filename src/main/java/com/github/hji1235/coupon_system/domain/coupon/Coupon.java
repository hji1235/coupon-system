package com.github.hji1235.coupon_system.domain.coupon;

import com.github.hji1235.coupon_system.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    private int discountAmount;

    private Integer minOrderPrice;

    private Integer maxCount;

    private Integer maxCountPerMember;

    private int allocatedCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssuerType issuerType;

    @Column(nullable = false)
    private Long issuerId;

    @Embedded
    private ExpirationPolicy expirationPolicy;

    @Embedded
    private TimeLimitPolicy timeLimitPolicy;

    @Builder
    public Coupon(String name, int discountAmount, Integer minOrderPrice,
                  Integer maxCount, Integer maxCountPerMember, DiscountType discountType,
                  IssuerType issuerType, Long issuerId, ExpirationPolicy expirationPolicy,
                  TimeLimitPolicy timeLimitPolicy) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderPrice = minOrderPrice;
        this.maxCount = maxCount;
        this.maxCountPerMember = maxCountPerMember;
        this.discountType = discountType;
        this.issuerType = issuerType;
        this.issuerId = issuerId;
        this.expirationPolicy = expirationPolicy;
        this.timeLimitPolicy = timeLimitPolicy;
    }

    public boolean canIssueCoupon(int count) {
        return (maxCount == null) || (allocatedCount + count <= maxCount);
    }

    public boolean canIssueCouponToMember(int memberCouponCount) {
        return (maxCountPerMember == null) || (memberCouponCount < maxCountPerMember);
    }

    public void increaseAllocatedCount(int count) {
        this.allocatedCount += count;
    }

    public boolean isBrandCoupon() {
        return this.issuerType == IssuerType.BRAND;
    }

    public boolean isAdminCoupon() {
        return this.issuerType == IssuerType.ADMIN;
    }
}
