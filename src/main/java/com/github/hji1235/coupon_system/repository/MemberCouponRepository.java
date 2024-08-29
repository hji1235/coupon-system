package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.coupon.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    @Query("select count(mc) from MemberCoupon mc where mc.member.id = :memberId and mc.coupon.id = :couponId")
    int countMemberCoupon(@Param("memberId") Long memberId, @Param("couponId") Long couponId);

    @Query("select mc from MemberCoupon mc join fetch mc.coupon c where mc.id = :memberCouponId")
    Optional<MemberCoupon> findMemberCoupon(@Param("memberCouponId") Long memberCouponId);

    @Query("select mc from MemberCoupon mc join mc.coupon c where mc.member.id = :memberId and mc.used = false")
    List<MemberCoupon> findAllMemberCouponByMemberId(@Param("memberId") Long memberId);

//    @Query("select mc from MemberCoupon mc where mc.couponCode = :couponCode")
//    Optional<MemberCoupon> findByCouponCode(@Param("couponCode") UUID couponCode);
    Optional<MemberCoupon> findByCouponCode(@Param("couponCode") UUID couponCode);
}
