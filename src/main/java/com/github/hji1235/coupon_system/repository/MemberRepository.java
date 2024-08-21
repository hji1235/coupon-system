package com.github.hji1235.coupon_system.repository;

import com.github.hji1235.coupon_system.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
