package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.store.BrandFindResponse;
import com.github.hji1235.coupon_system.controller.dto.store.BrandSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.store.BrandUpdateRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    /*
    브랜드 생성
     */
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ApiResponse<Long> createBrand(@Valid @RequestBody BrandSaveRequest brandSaveRequest) {
        Long savedBrandId = brandService.saveBrand(brandSaveRequest);
        return ApiResponse.success(savedBrandId);
    }

    /*
    브랜드 단일 조회
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/{brandId}")
    public ApiResponse<BrandFindResponse> getBrand(@PathVariable Long brandId) {
        BrandFindResponse brandFindResponse = brandService.findBrand(brandId);
        return ApiResponse.success(brandFindResponse);
    }

    /*
    브랜드 이름 수정
     */
    @Secured("ROLE_ADMIN")
    @PatchMapping("/{brandId}")
    public ApiResponse<Void> updateBrand(
            @PathVariable Long brandId,
            @Valid @RequestBody BrandUpdateRequest brandUpdateRequest
    ) {
        brandService.updateBrand(brandId, brandUpdateRequest);
        return ApiResponse.success();
    }

    /*
    브랜드 삭제
     */
//    @DeleteMapping("/{brandId}")
//    public ApiResponse<Void> deleteBrand(@PathVariable Long brandId) {
//        brandService.deleteBrand(brandId);
//        return ApiResponse.success();
//    }
}
