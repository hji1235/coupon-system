package com.github.hji1235.coupon_system.domain.store;

import com.github.hji1235.coupon_system.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private Menu(String name, Integer price, Store store) {
        this.name = name;
        this.price = price;
        this.store = store;
    }

    public static Menu of(String menuName, Integer price, Store store) {
        return new Menu(menuName, price, store);
    }

    public void updateMenu(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
