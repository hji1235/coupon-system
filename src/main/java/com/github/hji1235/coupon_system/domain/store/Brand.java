package com.github.hji1235.coupon_system.domain.store;

import com.github.hji1235.coupon_system.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Brand(String name) {
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
