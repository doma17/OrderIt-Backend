package inu.amigo.order_it.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReceiptDto {
    private OrderInfoDto orderInfo;

    public ReceiptDto(OrderInfoDto orderInfo) {
        this.orderInfo = orderInfo;
    }
}
