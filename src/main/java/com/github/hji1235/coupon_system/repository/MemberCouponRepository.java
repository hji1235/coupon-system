package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.coupon.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    @Query("select count(mc) from MemberCoupon mc where mc.member.id = :memberId and mc.coupon.id = :couponId")
    int countMemberCoupon(@Param("memberId") Long memberId, @Param("couponId") Long couponId);
}
