package inu.amigo.order_it.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Detail> details = new ArrayList<>();

    private int totalPrice;

    @Builder
    public Order(List<Detail> details, int totalPrice) {
        this.details = details;
        this.totalPrice = totalPrice;
    }
}
