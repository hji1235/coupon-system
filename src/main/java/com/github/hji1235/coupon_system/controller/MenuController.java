package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.MenuFindResponse;
import com.github.hji1235.coupon_system.controller.dto.MenuSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.MenuUpdateRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MenuController {//#

    private final MenuService menuService;

    /*
    메뉴 생성
     */
    @PostMapping("/stores/{storeId}/menus")
    public ApiResponse<Void> menuSave(
            @PathVariable Long storeId,
            @Valid @RequestBody MenuSaveRequest menuSaveRequest
    ) {
        menuService.saveMenu(storeId, menuSaveRequest);
        return ApiResponse.success();
    }

    /*
    메뉴 단일 조회
     */
    @GetMapping("/stores/{storeId}/menus/{menuId}")
    public ApiResponse<MenuFindResponse> menuDetails(
            @PathVariable Long storeId,
            @PathVariable Long menuId
    ) {
        MenuFindResponse findMenu = menuService.findMenu(storeId, menuId);
        return ApiResponse.success(findMenu);
    }

    /*
    메뉴 다중 조회
     */
    @GetMapping("/stores/{storeId}/menus")
    public ApiResponse<List<MenuFindResponse>> menuListForStore(@PathVariable Long storeId) {
        List<MenuFindResponse> findMenus = menuService.findAllMenusByStoreId(storeId);
        return ApiResponse.success(findMenus);
    }

    /*
    메뉴 이름 및 가격 수정
     */
    @PatchMapping("/stores/{storeId}/menus/{menuId}")
    public ApiResponse<Void> menuModify(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @Valid @RequestBody MenuUpdateRequest menuUpdateRequest
            ) {
        menuService.modifyMenu(storeId, menuId, menuUpdateRequest);
        return ApiResponse.success();
    }

    /*
    메뉴 삭제
     */
    @DeleteMapping("/stores/{storeId}/menus/{menuId}")
    public ApiResponse<Void> menuRemove(
            @PathVariable Long storeId,
            @PathVariable Long menuId
    ) {
        menuService.removeMenu(storeId, menuId);
        return ApiResponse.success();
    }
}
