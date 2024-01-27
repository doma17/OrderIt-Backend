package inu.amigo.order_it.item.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Option {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero(message = "Price must be a positive or zero.")
    private int price;

    private String name;

    private String description;

    // 옵션이 정수형으로 받을 수 있는지 확인.
    // e.g. HOT -> BOOLEAN
    //      SHOT -> INTEGER
    private NumericType numericType;

    @Builder
    public Option(String name, int price, String description, NumericType numericType) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.numericType = numericType;
    }
}
