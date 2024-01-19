package inu.amigo.order_it.item.service;


import inu.amigo.order_it.item.dto.ItemRequestDto;
import inu.amigo.order_it.item.dto.ItemResponseDto;
import inu.amigo.order_it.item.entity.Item;
import inu.amigo.order_it.item.entity.Menu;
import inu.amigo.order_it.item.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemResponseDto> getAllItems() {
        log.info("[getAllItems] return all items");

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        List<Item> itemList = itemRepository.findAll();

        for (Item item : itemList) {
            ItemResponseDto itemResponseDto = getItemResponseDto(item);
            itemResponseDtoList.add(itemResponseDto);
        }

        return itemResponseDtoList;
    }

    public List<ItemResponseDto> getItemsByMenu (Menu menu) {
        log.info("[getItemsByMenu] return items by menu");

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        List<Item> itemList = itemRepository.findItemsByMenu(menu);

        for (Item item : itemList) {
            ItemResponseDto itemResponseDto = getItemResponseDto(item);
            itemResponseDtoList.add(itemResponseDto);
        }

        return itemResponseDtoList;
    }

    private ItemResponseDto getItemResponseDto(Item item) {
        return ItemResponseDto.builder()
                .item_id(item.getId())
                .price(item.getPrice())
                .name(item.getName())
                .imagePath(item.getImagePath())
                .build();
    }

    public void createItem(ItemRequestDto itemRequestDto) {
        log.info("[createItem] item name = {}", itemRequestDto.getName());

        Item item = Item.builder()
                .name(itemRequestDto.getName())
                .price(itemRequestDto.getPrice())
                .imagePath(itemRequestDto.getImagePath())
                .menu(itemRequestDto.getMenu())
                .build();

        itemRepository.save(item);
    }

    public void deleteItem(Long itemId) {
        log.info("[deleteItem] is executed");

        if (itemRepository.existsById(itemId)) {
            log.error("[deleteItem] item is not existed, itemId = {}", itemId);
            throw new RuntimeException("item is not existed");
        }

        itemRepository.deleteById(itemId);
    }
}
