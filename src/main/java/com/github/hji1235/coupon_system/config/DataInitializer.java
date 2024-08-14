package com.github.hji1235.coupon_system.config;

import com.github.hji1235.coupon_system.domain.admin.Admin;
import com.github.hji1235.coupon_system.domain.store.Brand;
import com.github.hji1235.coupon_system.domain.store.Menu;
import com.github.hji1235.coupon_system.domain.store.Store;
import com.github.hji1235.coupon_system.repository.AdminRepository;
import com.github.hji1235.coupon_system.repository.BrandRepository;
import com.github.hji1235.coupon_system.repository.MenuRepository;
import com.github.hji1235.coupon_system.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final AdminRepository adminRepository;
    private final BrandRepository brandRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    @Order(1)
    public void initializeAdmin() {
        adminRepository.save(new Admin("bhc@gmial.com", "456456", "BHC"));
        adminRepository.save(new Admin("kyochon@gmial.com", "456456", "KYOCHON"));
        adminRepository.save(new Admin("bbq@gmial.com", "456456", "BBQ"));
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    @Order(2)
    public void initializeData() {
        String[] brands = new String[]{"BHC", "교촌", "BBQ"};
        String[] branches = new String[]{"부전점", "연산점", "해운대점", "시청점", "동래점"};
        String[] menuNames = new String[]{"후라이드", "양념", "콜라"};
        int[] prices = new int[]{15000, 17000, 2000};

        for (String brandName : brands) {
            Brand brand = new Brand(brandName);
            brandRepository.save(brand);

            for (String branchName : branches) {
                Store store = new Store(brandName + "-" + branchName, brand);
                storeRepository.save(store);

                for (int i=0; i<menuNames.length; i++) {
                    Menu menu = new Menu(menuNames[i], prices[i], store);
                    menuRepository.save(menu);
                }
            }
        }
    }
}
