package com.github.hji1235.coupon_system.controller.dto.store;

import com.github.hji1235.coupon_system.domain.store.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class StoreFindResponse {

    private Long id;

    private String name;

    public StoreFindResponse(Store store) {
        this.id = store.getId();
        this.name = store.getName();
    }
}
