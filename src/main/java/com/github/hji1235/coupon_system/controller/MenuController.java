package com.github.hji1235.coupon_system.controller;

import com.github.hji1235.coupon_system.controller.dto.store.MenuFindResponse;
import com.github.hji1235.coupon_system.controller.dto.store.MenuSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.store.MenuUpdateRequest;
import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores/{storeId}/menus")
public class MenuController {

    private final MenuService menuService;

    /*
    메뉴 생성
     */
    @Secured("ROLE_STORE")
    @PostMapping
    public ApiResponse<Long> createMenu(
            @PathVariable Long storeId,
            @Valid @RequestBody MenuSaveRequest menuSaveRequest
    ) {
        Long savedMenuId = menuService.saveMenu(storeId, menuSaveRequest);
        return ApiResponse.success(savedMenuId);
    }

    /*
    메뉴 단일 조회
     */
    @Secured("ROLE_MEMBER")
    @GetMapping("/{menuId}")
    public ApiResponse<MenuFindResponse> getMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId
    ) {
        MenuFindResponse menuFindResponse = menuService.findMenu(storeId, menuId);
        return ApiResponse.success(menuFindResponse);
    }

    /*
    메뉴 다중 조회
     */
    @Secured("ROLE_MEMBER")
    @GetMapping
    public ApiResponse<List<MenuFindResponse>> getAllMenus(@PathVariable Long storeId) {
        List<MenuFindResponse> menuFindResponses = menuService.findAllMenus(storeId);
        return ApiResponse.success(menuFindResponses);
    }

    /*
    메뉴 이름 및 가격 수정
     */
    @Secured("ROLE_STORE")
    @PatchMapping("/{menuId}")
    public ApiResponse<Void> updateMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @Valid @RequestBody MenuUpdateRequest menuUpdateRequest
    ) {
        menuService.updateMenu(storeId, menuId, menuUpdateRequest);
        return ApiResponse.success();
    }

    /*
    메뉴 삭제
     */
//    @DeleteMapping("/{menuId}")
//    public ApiResponse<Void> deleteMenu(
//            @PathVariable Long storeId,
//            @PathVariable Long menuId
//    ) {
//        menuService.deleteMenu(storeId, menuId);
//        return ApiResponse.success();
//    }
}
