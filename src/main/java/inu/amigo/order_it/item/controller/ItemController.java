package inu.amigo.order_it.item.controller;

import inu.amigo.order_it.item.dto.ItemRequestDto;
import inu.amigo.order_it.item.dto.ItemResponseDto;
import inu.amigo.order_it.item.entity.Category;
import inu.amigo.order_it.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Item API")
@RequestMapping("/api/item")
@RestController
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "모든 Item 조회")
    @GetMapping
    public List<ItemResponseDto> getAllItems() {
        return itemService.getAllItems();
    }

    @Operation(summary = "메뉴 Item 조회")
    @GetMapping("/{category}")
    public List<ItemResponseDto> getItemsByMenu(
            @Parameter(
                    name = "menu",
                    description = "조회할 메뉴",
                    required = true,
                    schema = @Schema(implementation = Category.class)
            )
            @PathVariable Category category) {
        return itemService.getItemsByMenu(category);
    }

    @Operation(summary = "메뉴 생성")
    @PostMapping
    public ResponseEntity<String> createItem(
            @Parameter(
                    name = "itemRequestDto",
                    description = "생성할 Item 정보",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ItemRequestDto.class)
                    )
            )
            ItemRequestDto itemRequestDto) {
        try {
            itemService.createItem(itemRequestDto);
            return new ResponseEntity<>("item is added", HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("item creation is failed", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "메뉴 삭제")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteItemById(
            @Parameter(
                    name = "itemId",
                    description = "삭제할 Item의 ID",
                    required = true,
                    schema = @Schema(implementation = Long.class)
            )
            @PathVariable Long itemId) {
        try {
            itemService.deleteItem(itemId);
            return new ResponseEntity<>("item is deleted", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>("item deleting is failed", HttpStatus.BAD_REQUEST);
        }
    }
}
