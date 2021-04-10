package com.system.dispatch.controllers;

import com.system.dispatch.ProductRepository;
import com.system.dispatch.models.Product;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private ProductRepository productRepository;

    public DevBootstrap(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() throws Exception {
        Product product = new Product();
        product.setName("Weed");
        product.setQuantity(10d);
        product.setCountry("Lithuania");
        product.setUnitOfMeasure("g");

        productRepository.save(product);

        Product product2 = new Product();
        product2.setName("Medicine");
        product2.setQuantity(15d);
        product2.setCountry("UK");
        product2.setUnitOfMeasure("tablets");
        productRepository.save(product2);
    }
}
