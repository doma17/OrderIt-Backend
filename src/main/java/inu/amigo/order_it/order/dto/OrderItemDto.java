package inu.amigo.order_it.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@Schema(description = "주문 상품에 대한 상세 정보")
public class OrderItemDto {

    @Schema(description = "상품의 ID")
    private Long itemId;

    @Schema(description = "상품의 수량")
    private int quantity;

    @Schema(description = "옵션 ID와 해당 옵션의 수량을 나타내는 맵")
    private Map<Long, Integer> selectedOptions;

    @Schema(description = "옵션의 총 가격")
    private int optionTotalPrice;
}