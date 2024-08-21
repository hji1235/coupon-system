package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.MenuFindResponse;
import com.github.hji1235.coupon_system.controller.dto.MenuSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.MenuUpdateRequest;
import com.github.hji1235.coupon_system.domain.store.Menu;
import com.github.hji1235.coupon_system.domain.store.Store;
import com.github.hji1235.coupon_system.global.exception.MenuNotFoundException;
import com.github.hji1235.coupon_system.global.exception.StoreNotFoundException;
import com.github.hji1235.coupon_system.repository.MenuRepository;
import com.github.hji1235.coupon_system.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void saveMenu(Long storeId, MenuSaveRequest menuSaveRequest) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
        menuRepository.save(new Menu(menuSaveRequest.getName(), menuSaveRequest.getPrice(), store));
    }

    public MenuFindResponse findMenu(Long storeId, Long menuId) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(menuId));
        return new MenuFindResponse(menu);
    }

    public List<MenuFindResponse> findAllMenusByStoreId(Long storeId) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
        return menuRepository.findMenus(storeId).stream()
                .map(MenuFindResponse::new)
                .toList();
    }

    @Transactional
    public void modifyMenu(Long storeId, Long menuId, MenuUpdateRequest menuUpdateRequest) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(menuId));
        menu.changeNameAndPrice(menuUpdateRequest.getName(), menuUpdateRequest.getPrice());
    }

    @Transactional
    public void removeMenu(Long storeId, Long menuId) {
        storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(menuId));
        menuRepository.delete(menu);
    }
}
