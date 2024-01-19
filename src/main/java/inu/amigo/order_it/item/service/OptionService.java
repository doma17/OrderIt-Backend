package inu.amigo.order_it.item.service;

import inu.amigo.order_it.item.entity.Item;
import inu.amigo.order_it.item.entity.Option;
import inu.amigo.order_it.item.repository.ItemRepository;
import inu.amigo.order_it.item.repository.OptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OptionService {

    private final ItemRepository itemRepository;

    private final OptionRepository optionRepository;

    @Autowired
    public OptionService(ItemRepository itemRepository, OptionRepository optionRepository) {
        this.itemRepository = itemRepository;
        this.optionRepository = optionRepository;
    }

    public List<Option> getAllOptions() {
        log.info("[getAllOptions]");

        return optionRepository.findAll();
    }

    public List<Option> getOptionsByItemId(Long itemId) {
        log.info("[getOptionsByItemId]");
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        if (optionalItem.isPresent()) {
            return optionalItem.get().getOptions();
        }
        else {
            log.error("[getOptionsByItemId]");
            throw new IllegalStateException("");
        }
    }
}
