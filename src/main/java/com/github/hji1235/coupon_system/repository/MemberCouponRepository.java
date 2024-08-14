package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.coupon.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
}
