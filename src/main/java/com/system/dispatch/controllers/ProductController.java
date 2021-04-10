package com.system.dispatch.controllers;

import com.system.dispatch.ProductRepository;
import com.system.dispatch.models.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class ProductController {

    public ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping("/products")
    public String index(Model model) {
        model.addAttribute("products", productRepository.findAll());

        return "products";
    }

    @RequestMapping("/products/{id}")
    public String view(@PathVariable String id, Model model) {
        model.addAttribute("product", productRepository.findById(Integer.valueOf(id)));

        return "product";
    }

    @RequestMapping(path="/products/add", method= RequestMethod.POST)
    public RedirectView add(Model model, @ModelAttribute Product product) {
        productRepository.save(product);

        return new RedirectView("/products");
    }

    @RequestMapping("createProduct")
    public String create(Model model) {
        model.addAttribute("product", new Product());

        return "productForm";
    }

    @RequestMapping("/products/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Optional<Product> optionalProduct = productRepository.findById(Integer.valueOf(id));

        optionalProduct.ifPresent(product -> model.addAttribute("product", product));

        return "productForm";
    }

    @DeleteMapping
    @RequestMapping("/products/delete/{id}")
    public RedirectView delete(@PathVariable String id, Model model) {
        productRepository.deleteById(Integer.valueOf(id));

        return new RedirectView("/products");
    }
}
