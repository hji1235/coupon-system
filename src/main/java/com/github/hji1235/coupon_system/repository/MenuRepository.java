package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.store.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m join fetch m.store s where s.id = :storeId")
    List<Menu> findMenus(@Param("storeId") Long storeId);
}
