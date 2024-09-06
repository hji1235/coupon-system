package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.store.StoreFindResponse;
import com.github.hji1235.coupon_system.controller.dto.store.StoreSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.store.StoreUpdateRequest;
import com.github.hji1235.coupon_system.domain.store.Brand;
import com.github.hji1235.coupon_system.domain.store.Store;
import com.github.hji1235.coupon_system.global.exception.BrandNotFoundException;
import com.github.hji1235.coupon_system.global.exception.StoreNotFoundException;
import com.github.hji1235.coupon_system.repository.BrandRepository;
import com.github.hji1235.coupon_system.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final BrandRepository brandRepository;

    @Transactional
    public Long saveStore(StoreSaveRequest storeSaveRequest) {
        Brand brand = findBrandById(storeSaveRequest.getBrandId());
        Store store = Store.of(storeSaveRequest.getName(), brand);
        return storeRepository.save(store).getId();
    }

    public StoreFindResponse findStore(Long storeId) {
        Store store = findStoreById(storeId);
        return new StoreFindResponse(store);
    }

    public List<StoreFindResponse> findAllStores() {
        return storeRepository.findAll().stream()
                .map(StoreFindResponse::new)
                .toList();
    }

    @Transactional
    public void updateStore(Long storeId, StoreUpdateRequest storeUpdateRequest) {
        Store store = findStoreById(storeId);
        store.updateName(storeUpdateRequest.getName());
    }

    @Transactional
    public void deleteStore(Long storeId) {
        Store store = findStoreById(storeId);
        storeRepository.delete(store);
    }

    private Store findStoreById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
    }

    private Brand findBrandById(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
    }
}
