package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.store.MenuFindResponse;
import com.github.hji1235.coupon_system.controller.dto.store.MenuSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.store.MenuUpdateRequest;
import com.github.hji1235.coupon_system.domain.store.Menu;
import com.github.hji1235.coupon_system.domain.store.Store;
import com.github.hji1235.coupon_system.global.exception.DuplicateMenuNameInStoreException;
import com.github.hji1235.coupon_system.global.exception.InvalidMenuAccessException;
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
    public Long saveMenu(Long storeId, MenuSaveRequest menuSaveRequest) {
        Store store = findStoreById(storeId);
        validateDuplicateMenuNameInStore(storeId, menuSaveRequest.getName());
        Menu menu = Menu.of(menuSaveRequest.getName(), menuSaveRequest.getPrice(), store);
        return menuRepository.save(menu).getId();
    }

    public MenuFindResponse findMenu(Long storeId, Long menuId) {
        Menu menu = findAndValidateMenu(storeId, menuId);
        return new MenuFindResponse(menu);
    }

    public List<MenuFindResponse> findAllMenus(Long storeId) {
        Store store = findStoreById(storeId);
        return menuRepository.findAllByStore(store).stream()
                .map(MenuFindResponse::new)
                .toList();
    }

    @Transactional
    public void updateMenu(Long storeId, Long menuId, MenuUpdateRequest menuUpdateRequest) {
        Menu menu = findAndValidateMenu(storeId, menuId);
        validateDuplicateMenuNameInStore(storeId, menuUpdateRequest.getName());
        menu.updateMenuInfo(menuUpdateRequest.getName(), menuUpdateRequest.getPrice());
    }

    @Transactional
    public void deleteMenu(Long storeId, Long menuId) {
        Menu menu = findAndValidateMenu(storeId, menuId);
        menuRepository.delete(menu);
    }

    private Menu findAndValidateMenu(Long storeId, Long menuId) {
        Store store = findStoreById(storeId);
        Menu menu = findMenuById(menuId);
        validateMenuBelongsToStore(store, menu);
        return menu;
    }

    private void validateMenuBelongsToStore(Store store, Menu menu) {
        if (!store.equals(menu.getStore())) {
            throw new InvalidMenuAccessException(store.getId(), menu.getId());
        }
    }

    private void validateDuplicateMenuNameInStore(Long storeId, String menuName) {
        if (menuRepository.existsByNameAndStoreId(menuName, storeId)) {
            throw new DuplicateMenuNameInStoreException(menuName);
        }
    }

    private Store findStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
    }

    private Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuNotFoundException(menuId));
    }
}
