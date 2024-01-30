package inu.amigo.order_it.item.dto;

import inu.amigo.order_it.item.entity.NumericType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OptionRequestDto {
    private String name;
    private int price;
    private String description;
    @Schema(example = "(INTEGER, BOOLEAN)")
    private NumericType numericType;
}
