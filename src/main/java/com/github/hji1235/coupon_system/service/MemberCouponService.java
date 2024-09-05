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
    public void saveMemberCouponCodes(Long couponId, MemberCouponCodeSaveRequest memberCouponCodeSaveRequest) {
        Coupon coupon = findCouponById(couponId);
        int issuanceCount = memberCouponCodeSaveRequest.getQuantity();
        if (!coupon.canIssueCoupon(issuanceCount)) {
            throw new CouponMaxCountOverException(coupon.getMaxCount(), coupon.getAllocatedCount(), issuanceCount);
        }
        List<MemberCoupon> memberCoupons = IntStream.range(0, issuanceCount)
                .mapToObj(op -> MemberCoupon.ofWithoutMember(coupon))
                .toList();
        int saveSize = memberCouponRepository.saveAll(memberCoupons).size();
        coupon.increaseAllocatedCount(saveSize);
    }

    @Transactional
    public void saveAndAllocateMemberCouponByClick(Long memberId, Long couponId) {
        Member member = findMemberById(memberId);
        Coupon coupon = findCouponById(couponId);
        int issuanceCount = 1;
        if (!coupon.canIssueCoupon(issuanceCount)) {
            throw new CouponMaxCountOverException(coupon.getMaxCount(), coupon.getAllocatedCount(), issuanceCount);
        }
        int memberCouponCount = memberCouponRepository.countMemberCoupon(memberId, couponId);
        if (!coupon.canIssueCouponToMember(memberCouponCount)) {
            throw new CouponMaxCountPerMemberOverException(coupon.getMaxCountPerMember(), memberCouponCount);
        }
        memberCouponRepository.save(MemberCoupon.ofWithMember(coupon, member));
        coupon.increaseAllocatedCount(issuanceCount);
    }

    @Transactional
    public void allocateMemberCouponByCode(Long memberId, MemberCouponAllocateRequest memberCouponAllocateRequest) {
        Member member = findMemberById(memberId);
        UUID couponCode = memberCouponAllocateRequest.getCouponCode();
        MemberCoupon memberCoupon = findMemberCouponByCouponCode(couponCode);
        if (memberCoupon.isAllocated()) {
            throw new AlreadyAllocatedCouponCodeException(couponCode);
        }
        memberCoupon.allocateMember(member);
    }

    public List<MemberCouponFindResponse> findAllMemberCoupons(Long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId).stream()
                .filter(memberCoupon -> !memberCoupon.isExpired())
                .map(MemberCouponFindResponse::new)
                .toList();
    }

    public List<MemberCouponAvailableCheckResponse> availableCheckMemberCoupons(Long memberId, Long storeId, Long orderId) {
        Store store = findStoreByIdWithBrand(storeId);
        Order order = findOrderByIdWithOrderMenus(orderId);
        int paymentAmount = order.calculateTotalPayment();
        return memberCouponRepository.findAllByMemberId(memberId).stream()
                .filter(memberCoupon -> !memberCoupon.isExpired())
                .map((memberCoupon -> {
                    boolean isAvailable = memberCoupon.isAvailable(store, paymentAmount);
                    return new MemberCouponAvailableCheckResponse(memberCoupon, isAvailable);
                }))
                .toList();
    }

    private Coupon findCouponById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private MemberCoupon findMemberCouponByCouponCode(UUID couponCode) {
        return memberCouponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new MemberCouponNotFoundException(couponCode));
    }

    private Store findStoreByIdWithBrand(Long storeId) {
        return storeRepository.findByIdWithBrand(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
    }

    private Order findOrderByIdWithOrderMenus(Long orderId) {
        return orderRepository.findByIdWithOrderMenus(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
