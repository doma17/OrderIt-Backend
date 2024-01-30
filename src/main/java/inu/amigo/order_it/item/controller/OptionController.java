package inu.amigo.order_it.item.controller;

import inu.amigo.order_it.item.dto.OptionRequestDto;
import inu.amigo.order_it.item.entity.Option;
import inu.amigo.order_it.item.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Option API")
@RequestMapping("/api/option")
@RestController
public class OptionController {

    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @Operation(summary = "모든 옵션 조회")
    @GetMapping
    public List<Option> getAllOptions() {
        return optionService.getAllOptions();
    }

    @Operation(summary = "아이템의 옵션 조회")
    @GetMapping("/{itemId}")
    public List<Option> getItemOptions(
            @Parameter(
                    name = "itemId",
                    description = "옵션을 조회할 아이템의 ID",
                    required = true,
                    schema = @Schema(implementation = Long.class)
            )
            @PathVariable Long itemId) {
        return optionService.getOptionsByItemId(itemId);
    }

    @Operation(summary = "새로운 옵션 생성")
    @PostMapping
    public ResponseEntity<Option> createOption(
            @Parameter(
                    name = "option",
                    description = "생성할 옵션 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = Option.class)
                    )
            )
            @RequestBody OptionRequestDto option) {

        Option createdOption = optionService.createOption(option);

        return ResponseEntity.ok(createdOption);
    }

    @Operation(summary = "아이템에 옵션 추가")
    @PostMapping("/{itemId}")
    public ResponseEntity<String> addOptionsToItem(
            @Parameter(
                    name = "itemId",
                    description = "옵션을 추가할 아이템의 ID",
                    required = true,
                    schema = @Schema(implementation = Long.class)
            )
            @PathVariable Long itemId,
            @Parameter(
                    name = "optionIds",
                    description = "추가할 옵션의 ID 목록",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = List.class, type = "array", example = "[1, 2, 3]")
                    )
            )
            @RequestBody List<Long> optionIds) {

        optionService.addOptionToItem(itemId, optionIds);

        return ResponseEntity.ok("Options added to the item successfully");
    }

    @Operation(summary = "옵션 삭제")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteOption(
            @Parameter(
                    name = "itemId",
                    description = "옵션을 삭제할 아이템의 ID",
                    required = true,
                    schema = @Schema(implementation = Long.class)
            )
            @PathVariable Long itemId) {

        try {
            optionService.deleteOption(itemId);
            return ResponseEntity.ok("Item is deleted");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.ofNullable("Item Id is null");
        }
    }

    @Operation(summary = "아이템의 옵션 매핑 해제")
    @PatchMapping("/{itemId}/unmap/{optionId}")
    public ResponseEntity<String> unmapOptionFromItem(
            @Parameter(
                    name = "itemId",
                    description = "옵션을 매핑 해제할 아이템의 ID",
                    required = true,
                    schema = @Schema(implementation = Long.class)
            )
            @PathVariable Long itemId,
            @Parameter(
                    name = "optionId",
                    description = "매핑을 해제할 옵션의 ID",
                    required = true,
                    schema = @Schema(implementation = Long.class)
            )
            @PathVariable Long optionId) {

        optionService.unmapOptionFromItem(itemId, optionId);

        return ResponseEntity.ok("Option unmapped from the item successfully");
    }
}
