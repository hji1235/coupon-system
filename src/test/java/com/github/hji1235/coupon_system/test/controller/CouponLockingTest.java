package com.github.hji1235.coupon_system.test.controller;

import com.github.hji1235.coupon_system.controller.MemberCouponController;
import com.github.hji1235.coupon_system.controller.converter.CouponConverter;
import com.github.hji1235.coupon_system.controller.dto.CustomUserInfoDto;
import com.github.hji1235.coupon_system.controller.dto.coupon.AdminCouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.MemberCouponCodeSaveRequest;
import com.github.hji1235.coupon_system.domain.coupon.Coupon;
import com.github.hji1235.coupon_system.domain.coupon.DiscountType;
import com.github.hji1235.coupon_system.domain.coupon.ExpirationPolicyType;
import com.github.hji1235.coupon_system.domain.coupon.IssuerType;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.domain.member.Role;
import com.github.hji1235.coupon_system.global.jwt.CustomUserDetails;
import com.github.hji1235.coupon_system.repository.CouponRepository;
import com.github.hji1235.coupon_system.repository.MemberCouponRepository;
import com.github.hji1235.coupon_system.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootTest
@ActiveProfiles("test")
class CouponLockingTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberCouponController memberCouponController;

    @Autowired
    private CouponConverter couponConverter;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @AfterEach
    void afterEach() {
        memberCouponRepository.deleteAll();
        couponRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("쿠폰 발급(클릭) - 비관적 락")
    void allocateMemberCouponByClickPessimisticLock() throws InterruptedException {
        for (int i=0; i<100; i++) {
            Member member = Member.of(i + "", i + "", i + "", Role.MEMBER);
            memberRepository.save(member);
        }
        AdminCouponSaveRequest adminCouponSaveRequest = createAdminCouponSaveRequest();
        Coupon coupon = couponConverter.convertToAdminCoupon(adminCouponSaveRequest);
        Long couponId = couponRepository.save(coupon).getId();

        int numberOfUsers = 100;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfUsers);
        CountDownLatch latch = new CountDownLatch(numberOfUsers);

        for (int i = 0; i < numberOfUsers; i++) {
            Long memberId = i + 1L;
            executor.submit(() -> {
                try {
                    Member member = Member.of("hji1235@naver.com", "456456456", "CMH", Role.MEMBER);
                    CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(member);
                    customUserInfoDto.setId(memberId);
                    CustomUserDetails customUserDetails = new CustomUserDetails(customUserInfoDto);
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
                    );
                    memberCouponController.allocateMemberCouponByClick(couponId, customUserDetails);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // Then
        Coupon findCoupon = couponRepository.findById(coupon.getId()).get();
        Assertions.assertThat(findCoupon.getAllocatedCount()).isEqualTo(100);
    }

    @Test
    @DisplayName("쿠폰 코드 발행 - 낙관적 락")
    void issueCouponCodesOptimisticLock() throws InterruptedException {
        // Given
        AdminCouponSaveRequest adminCouponSaveRequest = createAdminCouponSaveRequest();
        Coupon coupon = couponConverter.convertToAdminCoupon(adminCouponSaveRequest);
        Long couponId = couponRepository.save(coupon).getId();

        // When

        int numberOfUsers = 2;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfUsers);
        CountDownLatch latch = new CountDownLatch(numberOfUsers);
        CountDownLatch startLatch = new CountDownLatch(1);
        List<Exception> exceptions = Collections.synchronizedList(new ArrayList<>());

        for (int i=0; i<numberOfUsers; i++) {
            executor.execute(() -> {
                try {
                    Member member = Member.of("hji1235@naver.com", "456456456", "CMH", Role.ADMIN);
                    CustomUserInfoDto customUserInfoDto = new CustomUserInfoDto(member);
                    customUserInfoDto.setId(1L);
                    CustomUserDetails customUserDetails = new CustomUserDetails(customUserInfoDto);

                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities())
                    );
                    startLatch.await();
                    memberCouponController.issueCouponCodes(couponId, new MemberCouponCodeSaveRequest(100));
                } catch (Exception e) {
                    exceptions.add(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        startLatch.countDown();

        latch.await();
        executor.shutdown();

        Assertions.assertThat(exceptions)
                .hasSize(1)
                .first()
                .isInstanceOf(ObjectOptimisticLockingFailureException.class);

        Coupon findCoupon = couponRepository.findById(coupon.getId()).get();
        Assertions.assertThat(findCoupon.getAllocatedCount()).isEqualTo(100);
    }

    private static AdminCouponSaveRequest createAdminCouponSaveRequest() {
        return AdminCouponSaveRequest.builder()
                .name("어드민 쿠폰")
                .discountAmount(10)
                .minOrderPrice(15000)
                .maxCount(100)
                .maxCountPerMember(2)
                .discountType(DiscountType.PERCENT)
                .issuerType(IssuerType.ADMIN)
                .issuerId(0L)
                .expirationPolicyType(ExpirationPolicyType.AFTER_ISSUE_DATE)
                .startAt(null)
                .expiredAt(null)
                .daysFromIssuance(30)
                .timeLimit(false)
                .timeLimitStartAt(null)
                .timeLimitEndAt(null)
                .build();
    }
}
