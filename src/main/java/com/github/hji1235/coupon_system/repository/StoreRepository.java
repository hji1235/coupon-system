package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
