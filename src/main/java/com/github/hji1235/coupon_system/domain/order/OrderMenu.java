package com.github.hji1235.coupon_system.domain.order;

import com.github.hji1235.coupon_system.domain.BaseEntity;
import com.github.hji1235.coupon_system.domain.store.Menu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_menu_id")
    private Long id;

    private int quantity;

    private int orderPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private OrderMenu(int quantity, int orderPrice, Order order, Menu menu) {
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.order = order;
        this.menu = menu;
    }

    public static OrderMenu of(int quantity, int orderPrice, Order order, Menu menu) {
        return new OrderMenu(quantity, orderPrice, order, menu);
    }

    public int menuPrice() {
        return orderPrice * quantity;
    }
}
