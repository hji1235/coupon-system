package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.store.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    boolean existsByName(String name);
}
