package com.github.hji1235.coupon_system.domain.order;

import com.github.hji1235.coupon_system.domain.BaseEntity;
import com.github.hji1235.coupon_system.domain.member.Member;
import com.github.hji1235.coupon_system.domain.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    private Order(Member member, Store store) {
        this.orderStatus = OrderStatus.CREATED;
        this.member = member;
        this.store = store;
    }

    public static Order of(Member member, Store store) {
        return new Order(member, store);
    }

    public int calculateTotalPayment() {
        int paymentAmount = 0;
        for (OrderMenu orderMenu : orderMenus) {
            paymentAmount += orderMenu.menuPrice();
        }
        return paymentAmount;
    }

    public void pendingOrder() {
        orderStatus = OrderStatus.PENDING;
    }
}
