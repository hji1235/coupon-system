package com.github.hji1235.coupon_system.service;

import com.github.hji1235.coupon_system.controller.dto.StoreFindResponse;
import com.github.hji1235.coupon_system.controller.dto.StoreSaveRequest;
import com.github.hji1235.coupon_system.controller.dto.StoreUpdateRequest;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final BrandRepository brandRepository;

    @Transactional
    public void saveStore(StoreSaveRequest storeSaveRequest) {
        Long brandId = storeSaveRequest.getBrandId();
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
        storeRepository.save(new Store(storeSaveRequest.getName(), brand));
    }

    public StoreFindResponse findStore(Long storeId) {
        Store store = storeRepository.findStore(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
        return new StoreFindResponse(store);
    }

    public List<StoreFindResponse> findAllStores() {
        return storeRepository.findAllStores().stream()
                .map(StoreFindResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void modifyStore(Long storeId, StoreUpdateRequest storeUpdateRequest) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
        store.changeName(storeUpdateRequest.getName());
    }

    @Transactional
    public void removeStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(storeId));
        storeRepository.delete(store);
    }
}
