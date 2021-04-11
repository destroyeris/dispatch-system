package com.system.dispatch.controllers;

import com.system.dispatch.repositories.ItemRepository;
import com.system.dispatch.models.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class ItemController {

    public ItemRepository productRepository;

    public ItemController(ItemRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping("/items")
    public String index(Model model) {
        model.addAttribute("items", productRepository.findAll());

        return "items/items";
    }

    @RequestMapping("/items/{id}")
    public String view(@PathVariable String id, Model model) {
        Optional<Item> optionalProduct = productRepository.findById(Integer.valueOf(id));
        optionalProduct.ifPresent(product -> model.addAttribute("item", product));

        return "items/item";
    }

    @RequestMapping(path="/items/add", method= RequestMethod.POST)
    public RedirectView add(Model model, @ModelAttribute Item product) {
        productRepository.save(product);

        return new RedirectView("/items");
    }

    @RequestMapping("createItem")
    public String create(Model model) {
        model.addAttribute("item", new Item());

        return "items/itemForm";
    }

    @RequestMapping("/items/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Optional<Item> optionalProduct = productRepository.findById(Integer.valueOf(id));

        optionalProduct.ifPresent(product -> model.addAttribute("item", product));

        return "items/itemForm";
    }

    @DeleteMapping
    @RequestMapping("/items/delete/{id}")
    public RedirectView delete(@PathVariable String id, Model model) {
        productRepository.deleteById(Integer.valueOf(id));

        return new RedirectView("/items");
    }
}
