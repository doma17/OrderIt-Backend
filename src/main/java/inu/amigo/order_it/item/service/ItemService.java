package inu.amigo.order_it.item.service;


import inu.amigo.order_it.item.dto.ItemRequestDto;
import inu.amigo.order_it.item.dto.ItemResponseDto;
import inu.amigo.order_it.item.entity.Item;
import inu.amigo.order_it.item.entity.Category;
import inu.amigo.order_it.item.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Item 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Slf4j
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * 모든 아이템을 조회하여 DTO 형태로 반환
     *
     * @return 모든 아이템의 DTO 목록
     */
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

    /**
     * 메뉴에 해당하는 아이템을 조회하여 DTO 형태로 반환
     *
     * @param category 조회할 메뉴
     * @return 메뉴에 해당하는 아이템의 DTO 목록
     */
    public List<ItemResponseDto> getItemsByMenu (Category category) {
        log.info("[getItemsByMenu] return items by menu");

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        List<Item> itemList = itemRepository.findItemsByMenu(category);

        for (Item item : itemList) {
            ItemResponseDto itemResponseDto = getItemResponseDto(item);
            itemResponseDtoList.add(itemResponseDto);
        }

        return itemResponseDtoList;
    }

    /**
     * 새로운 아이템을 생성
     *
     * @param itemRequestDto 생성할 아이템 정보를 담은 DTO
     */
    public void createItem(ItemRequestDto itemRequestDto) {
        log.info("[createItem] item name = {}", itemRequestDto.getName());

        validateCreateItem(itemRequestDto);

        Item item = Item.builder()
                .name(itemRequestDto.getName())
                .price(itemRequestDto.getPrice())
                .imagePath(itemRequestDto.getImagePath())
                .category(itemRequestDto.getCategory())
                .build();

        itemRepository.save(item);
    }

    /**
     * 아이템 삭제
     *
     * @param itemId 삭제할 아이템의 ID
     */
    public void deleteItem(Long itemId) {
        log.info("[deleteItem] is executed");

        if (!itemRepository.existsById(itemId)) {
            log.error("[deleteItem] Item is not existed, itemId = {}", itemId);
            throw new EntityNotFoundException("Item not found with id: " + itemId);
        }

        itemRepository.deleteById(itemId);
    }

    /**
     * Item 엔티티를 ItemResponseDto로 변환
     *
     * @param item 변환할 Item 엔티티
     * @return ItemResponseDto로 변환된 객체
     */
    private ItemResponseDto getItemResponseDto(Item item) {
        return ItemResponseDto.builder()
                .item_id(item.getId())
                .price(item.getPrice())
                .name(item.getName())
                .imagePath(item.getImagePath())
                .build();
    }

    /**
     * 아이템 생성 시 유효성 검사를 수행
     *
     * @param itemRequestDto 생성할 아이템 정보를 담은 DTO
     */
    private static void validateCreateItem(ItemRequestDto itemRequestDto) {
        if (itemRequestDto.getName() == null || itemRequestDto.getPrice() <= 0 || itemRequestDto.getCategory() == null) {
            log.error("[createItem] Required parameter is missing");
            throw new IllegalStateException("[createItem] Required parameter is missing");
        }

        if (itemRequestDto.getPrice() > 100000) {
            log.error("[createItem] Price is too high");
            throw new IllegalStateException("[createItem] Price is too high");
        }
    }
}
