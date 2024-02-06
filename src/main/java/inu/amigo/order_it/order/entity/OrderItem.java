package inu.amigo.order_it.order.entity;

import inu.amigo.order_it.item.entity.Item;
import inu.amigo.order_it.item.entity.Option;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Map;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn
    private Item item;

    @Positive
    private int quantity;

    @ElementCollection
    @CollectionTable(name = "order_item_options", joinColumns = @JoinColumn(name = "order_item_id"))
    @MapKeyColumn(name = "option_id")
    @Column(name = "option_value")
    private Map<Option, Integer> selectedOptions;

    private int optionTotalPrice;
}
