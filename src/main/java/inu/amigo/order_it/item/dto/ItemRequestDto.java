package inu.amigo.order_it.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import inu.amigo.order_it.item.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class ItemRequestDto {
    @Schema(description = "Item의 영어 이름", example = "Ice_Americano")
    private String eng_name;

    @Schema(description = "Item의 한글 이름", example = "아이스 아메리카노")
    private String kor_name;

    @Schema(description = "Item의 가격", example = "3000")
    private int price;

    @Schema(description = "Item의 이미지 경로", example = "example.jpg")
    @JsonProperty("imagePath")
    private String imagePath;

    @Schema(description = "Item의 메뉴", example = "Hot")
    private Category category;
}