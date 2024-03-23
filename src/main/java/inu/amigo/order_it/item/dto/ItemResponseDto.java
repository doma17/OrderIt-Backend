package inu.amigo.order_it.item.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ItemResponseDto {
    @Schema(description = "Item의 식별자")
    private Long item_id;

    @Schema(description = "Item의 이름", example = "아메리카노")
    private String name;

    @Schema(description = "Item의 가격", example = "3000")
    private int price;

    @Schema(description = "Item의 이미지 경로", example = "americano.jpg")
    private String imagePath;

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
