package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.store.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
