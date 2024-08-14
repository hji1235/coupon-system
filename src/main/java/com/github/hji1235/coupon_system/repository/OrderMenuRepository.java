package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.order.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
