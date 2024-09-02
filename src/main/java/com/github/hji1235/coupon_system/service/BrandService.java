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
        Brand newBrand = new Brand(brandSaveRequest.getName());
        Brand savedBrand = brandRepository.save(newBrand);
        return savedBrand.getId();
    }

    public BrandFindResponse findBrand(Long brandId) {
        return brandRepository.findById(brandId)
                .map(BrandFindResponse::new)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
    }

    @Transactional
    public void modifyBrand(Long brandId, BrandUpdateRequest brandUpdateRequest) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
        brand.changeName(brandUpdateRequest.getName());
    }

    @Transactional
    public void removeBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
        brandRepository.delete(brand);
    }
}
