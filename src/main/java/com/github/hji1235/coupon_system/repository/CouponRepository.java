package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
