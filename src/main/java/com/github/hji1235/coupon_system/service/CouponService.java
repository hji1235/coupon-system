package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.converter.CouponConverter;
import com.github.hji1235.coupon_system.controller.dto.AdminCouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.StoreCouponSaveRequest;
import com.github.hji1235.coupon_system.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponConverter couponConverter;

    @Transactional
    public void adminCouponSave(AdminCouponSaveRequest adminCouponSaveRequest) {
        couponRepository.save(couponConverter.convertToAdminCoupon(adminCouponSaveRequest));
    }

    @Transactional
    public void storeCouponSave(Long storeId, StoreCouponSaveRequest storeCouponSaveRequest) {
        couponRepository.save(couponConverter.convertToStoreCoupon(storeId, storeCouponSaveRequest));
    }
}
