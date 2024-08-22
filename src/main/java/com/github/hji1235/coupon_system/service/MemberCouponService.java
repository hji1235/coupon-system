package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.domain.coupon.Coupon;
import com.github.hji1235.coupon_system.domain.coupon.MemberCoupon;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.global.exception.CouponMaxCountOverException;
import com.github.hji1235.coupon_system.global.exception.CouponMaxCountPerMemberOverException;
import com.github.hji1235.coupon_system.global.exception.CouponNotFoundException;
import com.github.hji1235.coupon_system.global.exception.MemberNotFoundException;
import com.github.hji1235.coupon_system.repository.CouponRepository;
import com.github.hji1235.coupon_system.repository.MemberCouponRepository;
import com.github.hji1235.coupon_system.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public void saveMemberCoupon(Long memberId, Long couponId) {
        int issuanceCount = 1;
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));
        if (!coupon.canIssueCoupon(issuanceCount)) {
            throw new CouponMaxCountOverException(coupon.getMaxCount(), coupon.getAllocatedCount(), issuanceCount);
        }
        int memberCouponCount = memberCouponRepository.countMemberCoupon(memberId, couponId);
        if (!coupon.canIssueCouponToMember(memberCouponCount)) {
            throw new CouponMaxCountPerMemberOverException(coupon.getMaxCountPerMember(), memberCouponCount);
        }
        memberCouponRepository.save(new MemberCoupon(coupon, member));
        coupon.increaseAllocatedCount(issuanceCount);
    }
}
