package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.BrandFindResponse;
import com.github.hji1235.coupon_system.controller.dto.BrandSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.BrandUpdateRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BrandController {

    private final BrandService brandService;

    /*
    브랜드 생성
     */
    @PostMapping("/brands")
    public ApiResponse<Long> brandSave(@Valid @RequestBody BrandSaveRequest brandSaveRequest) {
        Long savedBrandId = brandService.saveBrand(brandSaveRequest);
        return ApiResponse.success(savedBrandId);
    }

    /*
    브랜드 단일 조회
     */
    @GetMapping("/brands/{brandId}")
    public ApiResponse<BrandFindResponse> brandDetails(@PathVariable Long brandId) {
        BrandFindResponse findBrand = brandService.findBrand(brandId);
        return ApiResponse.success(findBrand);
    }

    /*
    브랜드 이름 수정
     */
    @PatchMapping("/brands/{brandId}")
    public ApiResponse<Void> brandModify(
            @PathVariable Long brandId,
            @Valid @RequestBody BrandUpdateRequest brandUpdateRequest
            ) {
        brandService.modifyBrand(brandId, brandUpdateRequest);
        return ApiResponse.success();
    }

    /*
    브랜드 삭제
     */
    @DeleteMapping("/brands/{brandId}")
    public ApiResponse<Void> brandRemove(@PathVariable Long brandId) {
        brandService.removeBrand(brandId);
        return ApiResponse.success();
    }
}
