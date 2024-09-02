package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.*;
import com.github.hji1235.coupon_system.domain.coupon.Coupon;
import com.github.hji1235.coupon_system.domain.coupon.MemberCoupon;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.domain.order.Order;
import com.github.hji1235.coupon_system.domain.store.Store;
import com.github.hji1235.coupon_system.global.exception.*;
import com.github.hji1235.coupon_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

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

    public MemberCouponCheckResponse checkMemberCoupons(Long memberId, Long storeId, Long orderId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        Store store = storeRepository.findByIdWithBrand(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
        Order order = orderRepository.findOrder(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        int paymentAmount = order.calculateTotalPayment();
        List<MemberCouponCheckDto> checkedMemberCoupons = memberCouponRepository.findAllMemberCouponByMemberId(memberId).stream()
                .map((memberCoupon -> createMemberCouponCheckDto(memberCoupon, store, paymentAmount)))
                .toList();
        return new MemberCouponCheckResponse(checkedMemberCoupons);
    }

    public MemberCouponCheckDto createMemberCouponCheckDto(MemberCoupon memberCoupon, Store store, int paymentAmount) {
        Long issuerId = store.getId();
        if (memberCoupon.getCoupon().isBrandCoupon()) {
            issuerId = store.getBrand().getId();
        }
        return new MemberCouponCheckDto(memberCoupon, issuerId, paymentAmount);
    }

    @Transactional
    public void saveMemberCouponCodes(Long couponId, MemberCouponCodeSaveRequest memberCouponCodeSaveRequest) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));
        int issuanceCount = memberCouponCodeSaveRequest.getQuantity();
        if (!coupon.canIssueCoupon(issuanceCount)) {
            throw new CouponMaxCountOverException(coupon.getMaxCount(), coupon.getAllocatedCount(), issuanceCount);
        }
        List<MemberCoupon> memberCoupons = IntStream.range(0, issuanceCount)
                .mapToObj(op -> new MemberCoupon(coupon, null))
                .toList();
        int saveSize = memberCouponRepository.saveAll(memberCoupons).size();
        coupon.increaseAllocatedCount(saveSize);
    }

    @Transactional
    public void allocateMemberCoupon(Long memberId, MemberCouponAllocateRequest memberCouponAllocateRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        UUID couponCode = memberCouponAllocateRequest.getCouponCode();
        MemberCoupon memberCoupon = memberCouponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new MemberCouponNotFoundException(couponCode));
        memberCoupon.allocateMember(member);
    }

    public List<MemberCouponFindAllResponse> findAllMemberCoupons(Long memberId) {
        return memberCouponRepository.findAllMemberCouponByMemberId(memberId).stream()
                .filter(memberCoupon -> !memberCoupon.isExpired())
                .map(MemberCouponFindAllResponse::new)
                .toList();
    }
}
