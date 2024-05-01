package inu.amigo.order_it.item.entity;


import inu.amigo.order_it.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String eng_name;

    @Column(unique = true)
    private String kor_name;

    @PositiveOrZero(message = "Price must be a positive or zero.")
    private int price;

    private String imagePath;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Builder
    public Item(String eng_name, String kor_name, int price, String imagePath, Category category) {
        this.eng_name = eng_name;
        this.kor_name = kor_name;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
    }

    public Item updateItem(String eng_name, String kor_name, int price, String imagePath, Category category) {
        this.eng_name = eng_name;
        this.kor_name = kor_name;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
        return this;
    }
}
