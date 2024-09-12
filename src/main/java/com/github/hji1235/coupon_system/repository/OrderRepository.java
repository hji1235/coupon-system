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

    @Query("select distinct o from Order o " +
            "join fetch o.store " +
            "where o.member.id = :memberId and o.orderStatus != com.github.hji1235.coupon_system.domain.order.OrderStatus.CREATED")
    List<Order> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("select o from Order o " +
            "join fetch o.store s " +
            "left join fetch o.payment p " +
            "left join fetch p.memberCoupon mc " +
            "left join fetch mc.coupon " +
            "where o.id = :orderId and o.member.id = :memberId")
    Optional<Order> findByOrderAndMemberWithPayment(@Param("orderId") Long orderId, @Param("memberId") Long memberId);

    @Query("select o " +
            "from Order o " +
            "join fetch o.orderMenus om " +
            "join fetch om.menu m " +
            "where o.store.id = :storeId " +
            "and (o.orderStatus = com.github.hji1235.coupon_system.domain.order.OrderStatus.PENDING " +
            "or o.orderStatus = com.github.hji1235.coupon_system.domain.order.OrderStatus.PREPARING)")
    List<Order> findAllByStoreId(@Param("storeId") Long storeId);
}
