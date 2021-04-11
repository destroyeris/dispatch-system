package com.system.dispatch.bootstrap;

import com.system.dispatch.repositories.ItemRepository;
import com.system.dispatch.models.Item;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private ItemRepository productRepository;

    public DevBootstrap(ItemRepository productRepository) {
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
        Item product = new Item();
        product.setName("Weed");
        product.setAmount(10d);
        product.setCountry("Lithuania");
        product.setPicture("https://cms.qz.com/wp-content/uploads/2019/10/Recreational-Weed-e1570153378769.jpg?quality=75&strip=all&w=1200&h=900&crop=1");
        product.setUnitOfMeasurement("g");

        productRepository.save(product);

        Item product2 = new Item();
        product2.setName("Medicine");
        product2.setPicture("https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/c496dc3199003.5acdbd673bd52.jpg");
        product2.setAmount(15d);
        product2.setCountry("UK");
        product2.setUnitOfMeasurement("tablets");
        productRepository.save(product2);
    }
}
