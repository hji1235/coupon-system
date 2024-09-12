package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.converter.CouponConverter;
import com.github.hji1235.coupon_system.controller.dto.coupon.AdminCouponSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.coupon.StoreCouponDiscountDetailDto;
import com.github.hji1235.coupon_system.controller.dto.coupon.StoreCouponSaveRequest;
import com.github.hji1235.coupon_system.domain.coupon.Coupon;
import com.github.hji1235.coupon_system.global.exception.DuplicateStoreCouponConditionException;
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
    public Long adminCouponSave(AdminCouponSaveRequest adminCouponSaveRequest) {
        Coupon coupon = couponConverter.convertToAdminCoupon(adminCouponSaveRequest);
        return couponRepository.save(coupon).getId();
    }

    @Transactional
    public void storeCouponSave(Long storeId, StoreCouponSaveRequest storeCouponSaveRequest) {
        StoreCouponDiscountDetailDto prevDto = null;
        for (StoreCouponDiscountDetailDto dto : storeCouponSaveRequest.getDiscountDetails()) {
            validateDuplicateCondition(prevDto, dto);
            prevDto = dto;
            Coupon coupon = couponConverter.convertToStoreCoupon(storeId, storeCouponSaveRequest, dto);
            couponRepository.save(coupon);
        }
    }

    private void validateDuplicateCondition(StoreCouponDiscountDetailDto prevDto, StoreCouponDiscountDetailDto dto) {
        if (dto.equals(prevDto)) {
            if (dto.getMinOrderPrice() == null) {
                throw new DuplicateStoreCouponConditionException(dto.getDiscountAmount(), 0);
            } else {
                throw new DuplicateStoreCouponConditionException(dto.getDiscountAmount(), dto.getMinOrderPrice());
            }
        }
    }
}
