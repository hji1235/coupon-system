package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.StoreFindResponse;
import com.github.hji1235.coupon_system.controller.dto.StoreSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.StoreUpdateRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("")
    public ApiResponse<Void> storeSave(@Valid @RequestBody StoreSaveRequest storeSaveRequest) {
        storeService.saveStore(storeSaveRequest);
        return ApiResponse.success();
    }

    @GetMapping("/{storeId}")
    public ApiResponse<StoreFindResponse> storeDetails(@PathVariable Long storeId) {
        StoreFindResponse findStore = storeService.findStore(storeId);
        return ApiResponse.success(findStore);
    }

    @GetMapping("")
    public ApiResponse<List<StoreFindResponse>> storeList() {
        List<StoreFindResponse> findStores = storeService.findAllStores();
        return ApiResponse.success(findStores);
    }

    @PatchMapping("/{storeId}")
    public ApiResponse<Void> storeModify(
            @PathVariable Long storeId,
            @Valid @RequestBody StoreUpdateRequest storeUpdateRequest
            ) {
        storeService.modifyStore(storeId, storeUpdateRequest);
        return ApiResponse.success();
    }

    @DeleteMapping("/{storeId}")
    public ApiResponse<Void> storeRemove(@PathVariable Long storeId) {
        storeService.removeStore(storeId);
        return ApiResponse.success();
    }
}
