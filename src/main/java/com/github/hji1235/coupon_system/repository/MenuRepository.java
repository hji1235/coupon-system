package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.store.Menu;
import com.github.hji1235.coupon_system.domain.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStore(Store store);

    @Query("select m from Menu m join fetch m.store where m.id = :menuId")
    Optional<Menu> findByIdWithStore(@Param("menuId") Long menuId);
}
