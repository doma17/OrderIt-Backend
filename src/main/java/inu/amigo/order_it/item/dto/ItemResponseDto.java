package inu.amigo.order_it.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ItemResponseDto {
    private Long item_id;
    private String name;
    private int price;
    private String imagePath;
}
