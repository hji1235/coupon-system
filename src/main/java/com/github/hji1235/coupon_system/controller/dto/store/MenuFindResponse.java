package com.github.hji1235.coupon_system.controller.dto.store;

import com.github.hji1235.coupon_system.domain.store.Menu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuFindResponse {

    private Long id;

    private String name;

    private Integer price;

    public MenuFindResponse(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
    }
}
