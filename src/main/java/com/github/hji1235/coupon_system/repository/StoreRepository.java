package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s join fetch s.brand b where s.id = :storeId")
    Optional<Store> findStore(@Param("storeId") Long storeId);

    @Query("select s from Store s join fetch s.brand b")
    List<Store> findAllStores();
}
