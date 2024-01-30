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

    @Singular("option")
    @ManyToMany
    @JoinTable(
            name = "item_option",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    private List<Option> options;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Builder
    public Item(String name, int price, String imagePath, List<Option> options, Category category) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.options = options;
        this.category = category;
    }
}
