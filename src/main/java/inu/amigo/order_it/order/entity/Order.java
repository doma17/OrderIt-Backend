package inu.amigo.order_it.order.entity;

import inu.amigo.order_it.global.BaseTimeEntity;
import inu.amigo.order_it.order.dto.OrderType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Detail> details = new ArrayList<>();

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Builder
    public Order(List<Detail> details, int totalPrice, OrderType orderType) {
        this.details = details;
        this.totalPrice = totalPrice;
        this.orderType = orderType;
    }
}
