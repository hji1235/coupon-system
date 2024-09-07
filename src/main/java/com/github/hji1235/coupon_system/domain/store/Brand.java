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

    @Column(nullable = false, unique = true, length = 30)
    private String name;

    private Brand(String name) {
        this.name = name;
    }

    public static Brand of(String brandName) {
        return new Brand(brandName);
    }

    public void updateBrandInfo(String name) {
        this.name = name;
    }
}
