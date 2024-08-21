package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
