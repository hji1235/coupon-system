package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.store.BrandFindResponse;
import com.github.hji1235.coupon_system.controller.dto.store.BrandSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.store.BrandUpdateRequest;
import com.github.hji1235.coupon_system.domain.store.Brand;
import com.github.hji1235.coupon_system.global.exception.BrandNotFoundException;
import com.github.hji1235.coupon_system.global.exception.DuplicateBrandNameException;
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
        validateDuplicateBrandName(brandSaveRequest.getName());
        Brand brand = Brand.of(brandSaveRequest.getName());
        return brandRepository.save(brand).getId();
    }

    public BrandFindResponse findBrand(Long brandId) {
        Brand brand = findBrandById(brandId);
        return new BrandFindResponse(brand);
    }

    @Transactional
    public void updateBrand(Long brandId, BrandUpdateRequest brandUpdateRequest) {
        validateDuplicateBrandName(brandUpdateRequest.getName());
        Brand brand = findBrandById(brandId);
        brand.updateBrandInfo(brandUpdateRequest.getName());
    }

    @Transactional
    public void deleteBrand(Long brandId) {
        Brand brand = findBrandById(brandId);
        brandRepository.delete(brand);
    }

    private void validateDuplicateBrandName(String brandName) {
        if (brandRepository.existsByName(brandName)) {
            throw new DuplicateBrandNameException(brandName);
        }
    }

    private Brand findBrandById(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
    }
}
