package inu.amigo.order_it.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDto {
    private Long itemId;

    private String name;

    private int quantity;

    private int price;
}
