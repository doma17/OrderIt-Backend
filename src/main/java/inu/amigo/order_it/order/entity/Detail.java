package inu.amigo.order_it.order.entity;

import inu.amigo.order_it.item.entity.Item;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Detail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Item item;

    private int quantity;

    @Builder
    public Detail(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
