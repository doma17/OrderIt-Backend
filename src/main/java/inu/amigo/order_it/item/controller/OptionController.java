package inu.amigo.order_it.item.controller;

import inu.amigo.order_it.item.entity.Option;
import inu.amigo.order_it.item.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/option")
@RestController
public class OptionController {

    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public List<Option> getAllOptions() {
        return optionService.getAllOptions();
    }

    @GetMapping("/{itemId}")
    public List<Option> getItemOptions(@PathVariable Long itemId) {
        return optionService.getOptionsByItemId(itemId);
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<String> addOptionsToItem(
            @PathVariable Long itemId,
            @RequestBody List<Long> optionIds) {

        optionService.addOptionToItem(itemId, optionIds);

        return ResponseEntity.ok("Options added to the item successfully");
    }
}
