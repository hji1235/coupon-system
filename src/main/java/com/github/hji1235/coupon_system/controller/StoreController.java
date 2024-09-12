package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.store.StoreFindResponse;
import com.github.hji1235.coupon_system.controller.dto.store.StoreSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.store.StoreUpdateRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    /*
    스토어 생성
     */
    @Secured("ROLE_STORE")
    @PostMapping
    public ApiResponse<Long> createStore(@Valid @RequestBody StoreSaveRequest storeSaveRequest) {
        Long savedStoreId = storeService.saveStore(storeSaveRequest);
        return ApiResponse.success(savedStoreId);
    }

    /*
    스토어 단일 조회
     */
    @Secured("ROLE_MEMBER")
    @GetMapping("/{storeId}")
    public ApiResponse<StoreFindResponse> getStore(@PathVariable Long storeId) {
        StoreFindResponse storeFindResponse = storeService.findStore(storeId);
        return ApiResponse.success(storeFindResponse);
    }

    /*
    스토어 다중 조회
     */
    @Secured("ROLE_MEMBER")
    @GetMapping
    public ApiResponse<List<StoreFindResponse>> getAllStores() {
        List<StoreFindResponse> storeFindResponses = storeService.findAllStores();
        return ApiResponse.success(storeFindResponses);
    }

    /*
    스토어 이름 수정
     */
    @Secured("ROLE_STORE")
    @PatchMapping("/{storeId}")
    public ApiResponse<Void> updateStore(
            @PathVariable Long storeId,
            @Valid @RequestBody StoreUpdateRequest storeUpdateRequest
    ) {
        storeService.updateStore(storeId, storeUpdateRequest);
        return ApiResponse.success();
    }

    /*
    스토어 삭제
     */
//    @DeleteMapping("/{storeId}")
//    public ApiResponse<Void> deleteStore(@PathVariable Long storeId) {
//        storeService.deleteStore(storeId);
//        return ApiResponse.success();
//    }
}
