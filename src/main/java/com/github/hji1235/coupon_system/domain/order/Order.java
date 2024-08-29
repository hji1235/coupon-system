package com.github.hji1235.coupon_system.domain.order;

import com.github.hji1235.coupon_system.domain.BaseEntity;
import com.github.hji1235.coupon_system.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

//    @Column(name = "start_at", nullable = false)
//    private LocalDateTime startAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private Payment payment;

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    public Order(Member member) {
        this.orderStatus = OrderStatus.PENDING;
        this.member = member;
    }

    public int calculateTotalPayment() {
        int paymentAmount = 0;
        for (OrderMenu orderMenu : orderMenus) {
            paymentAmount += orderMenu.menuPrice();
        }
        return paymentAmount;
    }

    public void prepareOrder() {
        orderStatus = OrderStatus.PREPARING;
    }
}
