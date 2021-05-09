package com.system.dispatch.controllers;

import com.system.dispatch.repositories.ItemRepository;
import com.system.dispatch.models.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository productRepository) {
        this.itemRepository = productRepository;
    }

    @RequestMapping("/items")
    public String index(Model model) {
        model.addAttribute("items", itemRepository.findAll());

        return "items/items";
    }

    @RequestMapping(path="/items/{id}", method = RequestMethod.GET)
    public String view(@PathVariable String id, Model model) {
        try {
            Optional<Item> optionalProduct = itemRepository.findById(Integer.valueOf(id));
            optionalProduct.ifPresent(product -> model.addAttribute("item", product));

            return "items/item";
        } catch (Exception e) {
            return "items";
        }
    }

    @RequestMapping(path = "/items/add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Item product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items/itemForm";
        }

        itemRepository.save(product);

        return "redirect:/items";
    }

    @RequestMapping("createItem")
    public String create(Model model) {
        model.addAttribute("item", new Item());

        return "items/itemForm";
    }

    @RequestMapping("/items/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Optional<Item> optionalProduct = itemRepository.findById(Integer.valueOf(id));

        optionalProduct.ifPresent(product -> model.addAttribute("item", product));

        return "items/itemForm";
    }

    @DeleteMapping
    @RequestMapping("/items/delete/{id}")
    public RedirectView delete(@PathVariable String id, Model model) {
        itemRepository.deleteById(Integer.valueOf(id));

        return new RedirectView("/items");
    }
}
