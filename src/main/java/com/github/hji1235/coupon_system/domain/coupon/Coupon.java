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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "discount_amount", nullable = false)
    private Integer discountAmount;

    @Column(name = "min_order_price")
    private Integer minOrderPrice;

    @Column(name = "max_count")
    private Integer maxCount;

    @Column(name = "max_count_per_mem")
    private Integer maxCountPerMember;

    @Column(name = "allocated_count")
    private Integer allocatedCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "issuer_type", nullable = false)
    private IssuerType issuerType;

    @Column(name = "issuer_id")
    private Long issuerId;

    @Embedded
    private ExpirationPolicy expirationPolicy;

    @Builder
    public Coupon(String name, Integer discountAmount, Integer minOrderPrice,
                  Integer maxCount, Integer maxCountPerMember, Integer allocatedCount,
                  DiscountType discountType, IssuerType issuerType, Long issuerId,
                  ExpirationPolicy expirationPolicy) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderPrice = minOrderPrice;
        this.maxCount = maxCount;
        this.maxCountPerMember = maxCountPerMember;
        this.allocatedCount = allocatedCount;
        this.discountType = discountType;
        this.issuerType = issuerType;
        this.issuerId = issuerId;
        this.expirationPolicy = expirationPolicy;
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
