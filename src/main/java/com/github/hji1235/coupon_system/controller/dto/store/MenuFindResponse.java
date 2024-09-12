package com.github.hji1235.coupon_system.controller.dto.store;

import com.github.hji1235.coupon_system.domain.store.Menu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MenuFindResponse {

    private Long id;

    private String name;

    private int price;

    public MenuFindResponse(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
    }

    public MenuFindResponse(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
