package inu.amigo.order_it.item.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @PositiveOrZero(message = "Price must be a positive or zero.")
    private int price;

    private String imagePath;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Builder
    public Item(Long id, String name, int price, String imagePath, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
    }
}
