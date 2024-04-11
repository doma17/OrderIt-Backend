package inu.amigo.order_it.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import inu.amigo.order_it.item.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemRequestDto {
    @Schema(description = "Item의 이름", example = "아메리카노")
    private String name;

    @Schema(description = "Item의 가격", example = "3000")
    private int price;

    @Schema(description = "Item의 이미지 경로", example = "example.jpg")
    @JsonProperty("imagePath")
    private String imagePath;

    @Schema(description = "Item의 메뉴", example = "Hot")
    private Category category;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Category getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}