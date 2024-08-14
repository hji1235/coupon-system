package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
