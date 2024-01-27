package inu.amigo.order_it.item.service;

import inu.amigo.order_it.item.entity.Item;
import inu.amigo.order_it.item.entity.Option;
import inu.amigo.order_it.item.repository.ItemRepository;
import inu.amigo.order_it.item.repository.OptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Option 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Slf4j
@Transactional
@Service
public class OptionService {

    private final ItemRepository itemRepository;

    private final OptionRepository optionRepository;

    @Autowired
    public OptionService(ItemRepository itemRepository, OptionRepository optionRepository) {
        this.itemRepository = itemRepository;
        this.optionRepository = optionRepository;
    }

    /**
     * 모든 옵션을 조회하여 반환
     *
     * @return 모든 옵션의 목록
     */
    public List<Option> getAllOptions() {
        log.info("[getAllOptions] is executed");

        return optionRepository.findAll();
    }

    /**
     * 특정 아이템에 연결된 옵션을 조회하여 반환
     *
     * @param itemId 아이템 ID
     * @return 아이템에 연결된 옵션 목록
     */
    public List<Option> getOptionsByItemId(Long itemId) {
        log.error("[getOptionsByItemId] is executed");
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (optionalItem.isPresent()) {
            return optionalItem.get().getOptions();
        }
        else {
            log.error("[getOptionsByItemId] Item not found with id : {}", itemId);
            throw new IllegalStateException("");
        }
    }

    public Option createOption(Option option) {
        return optionRepository.save(option);
    }

    /**
     * 특정 아이템에 옵션을 추가
     *
     * @param itemId    아이템 ID
     * @param optionIds 추가할 옵션 ID 목록
     */
    public void addOptionToItem(Long itemId, List<Long> optionIds) {
        log.info("[addOptionToItem] Item id : {}, Option Ids : {}", itemId, optionIds.toString());

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with id: " + itemId));

        List<Option> options = optionRepository.findAllById(optionIds);

        item.getOptions().addAll(options);

        itemRepository.save(item);
    }

    public void deleteOption(Long itemId) {
        log.info("[deleteOption] itemId : {}", itemId);

        if (itemId == null) {
            throw new IllegalArgumentException("[deleteOption] itemId is null !!");
        }

        itemRepository.deleteById(itemId);
    }

    @Transactional
    public void unmapOptionFromItem(Long itemId, Long optionId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        Optional<Option> optionalOption = optionRepository.findById(optionId);

        if (optionalItem.isPresent() && optionalOption.isPresent()) {
            Item item = optionalItem.get();
            Option option = optionalOption.get();

            // 아이템에 매핑된 옵션 목록에서 제거
            List<Option> itemOptions = item.getOptions();
            itemOptions.remove(option);

            // 아이템 저장 (옵션 매핑 해제)
            itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("Item or Option not found");
        }
    }
}
