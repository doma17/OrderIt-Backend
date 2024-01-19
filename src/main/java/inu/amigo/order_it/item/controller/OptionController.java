package inu.amigo.order_it.item.controller;

import inu.amigo.order_it.item.entity.Option;
import inu.amigo.order_it.item.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
