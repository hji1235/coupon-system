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
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    // 생성, 단일조회, 다중조회, 수정, 삭제

    @PostMapping("")
    public ApiResponse<Void> brandSave(@Valid @RequestBody BrandSaveRequest brandSaveRequest) {
        brandService.saveBrand(brandSaveRequest);
        return ApiResponse.success();
    }

    @GetMapping("/{brandId}")
    public ApiResponse<BrandFindResponse> brandDetails(@PathVariable Long brandId) {
        BrandFindResponse findBrand = brandService.findBrand(brandId);
        return ApiResponse.success(findBrand);
    }

    @PatchMapping("/{brandId}")
    public ApiResponse<Void> brandModify(
            @PathVariable Long brandId,
            @Valid @RequestBody BrandUpdateRequest brandUpdateRequest
            ) {
        brandService.modifyBrand(brandId, brandUpdateRequest);
        return ApiResponse.success();
    }

    @DeleteMapping("/{brandId}")
    public ApiResponse<Void> brandRemove(@PathVariable Long brandId) {
        brandService.removeBrand(brandId);
        return ApiResponse.success();
    }
}
