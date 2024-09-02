package com.github.hji1235.coupon_system.domain.store;

import com.github.hji1235.coupon_system.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private Store(String name, Brand brand) {
        this.name = name;
        this.brand = brand;
    }

    public static Store of(String storeName, Brand brand) {
        return new Store(storeName, brand);
    }

    public void updateName(String name) {
        this.name = name;
    }
}
