package inu.amigo.order_it.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderDto {

    private List<DetailDto> detailDtoList;

    private int totalPrice;

    private OrderType orderType;
}
