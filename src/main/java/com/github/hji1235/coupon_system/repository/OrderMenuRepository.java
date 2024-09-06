package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.order.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

    @Query("select om from OrderMenu om join fetch om.menu m where om.order.id = :orderId")
    List<OrderMenu> findByOrderId(@Param("orderId") Long orderId);
}
