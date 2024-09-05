package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.orderMenus om where o.id = :orderId")
    Optional<Order> findByIdWithOrderMenus(@Param("orderId") Long orderId);

    @Query("select distinct o from Order o join fetch o.member m join fetch o.orderMenus om join fetch om.menu mn join fetch mn.store where o.member.id = :memberId")
    List<Order> findOrders(@Param("memberId") Long memberId);

    @Query("select o from Order o join fetch o.payment p join fetch p.memberCoupon mc join fetch mc.coupon join fetch o.orderMenus om join fetch om.menu mn join fetch mn.store where o.id = :orderId")
    Optional<Order> findOrderDetails(@Param("orderId") Long orderId);
}
