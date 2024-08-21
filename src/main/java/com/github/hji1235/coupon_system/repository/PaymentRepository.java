package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
