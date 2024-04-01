package inu.amigo.order_it.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetailDto {

    @Schema(description = "주문 Item의 아이디", example = "1")
    private Long itemId;

    @Schema(description = "주문 Item의 개수", example = "3")
    private int quantity;
}
