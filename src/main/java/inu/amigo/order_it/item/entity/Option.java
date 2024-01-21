package inu.amigo.order_it.item.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Option {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private String description;

    // 옵션이 정수형으로 받을 수 있는지 확인.
    // e.g. HOT -> bool
    //      SHOT -> int
    private boolean isInteger;

    @ManyToMany(mappedBy = "options")
    private List<Item> items;

    @Builder
    public Option(String name, int price, String description, boolean isInteger, List<Item> items) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isInteger = isInteger;
        this.items = items;
    }
}
