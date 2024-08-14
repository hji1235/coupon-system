package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.BrandSaveRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
