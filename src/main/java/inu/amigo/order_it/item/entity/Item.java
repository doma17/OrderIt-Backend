package inu.amigo.order_it.item.entity;


import jakarta.persistence.*;
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

    private int price;
    private String imagePath;

    @Singular("option")
    @OneToMany
    private List<Option> options;

    @Enumerated(value = EnumType.STRING)
    private Menu menu;

    @Builder
    public Item(String name, int price, String imagePath, Menu menu) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.menu = menu;
    }
}
