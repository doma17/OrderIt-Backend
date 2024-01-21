package inu.amigo.order_it.item.controller;

import inu.amigo.order_it.item.dto.ItemRequestDto;
import inu.amigo.order_it.item.dto.ItemResponseDto;
import inu.amigo.order_it.item.entity.Menu;
import inu.amigo.order_it.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/item")
@RestController
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemResponseDto> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{menu}")
    public List<ItemResponseDto> getItemsByMenu(@PathVariable Menu menu) {
        return itemService.getItemsByMenu(menu);
    }

    @PostMapping
    public ResponseEntity<String> createItem(ItemRequestDto itemRequestDto) {
        try {
            itemService.createItem(itemRequestDto);
            return new ResponseEntity<>("item is added", HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("item creation is failed", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{itemId}")
    @ResponseBody
    public ResponseEntity<String> deleteItemById(@PathVariable Long itemId) {
        try {
            itemService.deleteItem(itemId);
            return new ResponseEntity<>("item is deleted", HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>("item deleting is failed", HttpStatus.BAD_REQUEST);
        }
    }
}
