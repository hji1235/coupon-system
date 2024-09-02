package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.BrandFindResponse;
import com.github.hji1235.coupon_system.controller.dto.BrandSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.BrandUpdateRequest;
import com.github.hji1235.coupon_system.domain.store.Brand;
import com.github.hji1235.coupon_system.global.exception.BrandNotFoundException;
import com.github.hji1235.coupon_system.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    public Long saveBrand(BrandSaveRequest brandSaveRequest) {
        Brand brand = Brand.of(brandSaveRequest.getName());
        return brandRepository.save(brand).getId();
    }

    public BrandFindResponse findBrand(Long brandId) {
        Brand brand = findBrandById(brandId);
        return new BrandFindResponse(brand);
    }

    @Transactional
    public void updateBrand(Long brandId, BrandUpdateRequest brandUpdateRequest) {
        Brand brand = findBrandById(brandId);
        brand.updateName(brandUpdateRequest.getName());
    }

    @Transactional
    public void deleteBrand(Long brandId) {
        Brand brand = findBrandById(brandId);
        brandRepository.delete(brand);
    }

    private Brand findBrandById(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
    }
}
