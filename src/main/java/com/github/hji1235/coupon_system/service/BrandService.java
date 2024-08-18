package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.BrandFindResponse;
import com.github.hji1235.coupon_system.controller.dto.BrandSaveRequest;
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

    public void saveBrand(BrandSaveRequest brandSaveRequest) {
        brandRepository.save(new Brand(brandSaveRequest.getName()));
    }

    public BrandFindResponse findBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
        return new BrandFindResponse(brand);
    }
}
