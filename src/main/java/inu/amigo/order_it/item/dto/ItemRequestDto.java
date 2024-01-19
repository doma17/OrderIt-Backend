package inu.amigo.order_it.item.dto;

import inu.amigo.order_it.item.entity.Menu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemRequestDto {
    private String name;
    private int price;
    private String imagePath;
    private Menu menu;
}
