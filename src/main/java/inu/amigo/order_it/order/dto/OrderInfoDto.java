package inu.amigo.order_it.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderInfoDto {
    private List<OrderItemDto> itemDtoList;
    private int totalPrice;
}
