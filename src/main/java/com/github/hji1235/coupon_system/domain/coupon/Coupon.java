package com.github.hji1235.coupon_system.domain.coupon;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

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
    private Integer MaxCountPerMember;

    @Column(name = "allocated_count")
    private Integer allocated_count;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "issuer_type", nullable = false)
    private IssuerType issuerType;

    @Column(name = "issuer_id")
    private Long IssuerId;

    @Embedded
    private ExpirationPolicy expirationPolicy;
}
