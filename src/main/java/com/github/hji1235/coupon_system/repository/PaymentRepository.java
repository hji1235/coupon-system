package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select p from Payment p join fetch p.order o join fetch o.store left join fetch p.memberCoupon mc left join fetch mc.coupon c where p.id = :paymentId")
    Optional<Payment> findByIdWithOrderAndMemberCoupon(@Param("paymentId") Long paymentId);
}
