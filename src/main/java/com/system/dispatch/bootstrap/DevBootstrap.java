package com.system.dispatch.bootstrap;

import com.system.dispatch.models.Auction;
import com.system.dispatch.models.Bid;
import com.system.dispatch.models.SoldItem;
import com.system.dispatch.repositories.AuctionRepository;
import com.system.dispatch.repositories.BidRepository;
import com.system.dispatch.repositories.ItemRepository;
import com.system.dispatch.models.Item;
import com.system.dispatch.repositories.SoldItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private ItemRepository productRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private SoldItemRepository soldItemRepository;

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
        initAuctions();

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


    private void initAuctions(){
        Item item1 = new Item(null, "Item1", 15.0, "kg", 9.5, "", "UK");
        productRepository.save(item1);
        SoldItem soldItem = new SoldItem(10.0, 1.0, item1);
        soldItemRepository.save(soldItem);

        Auction auction = new Auction(
                soldItem,
                LocalDateTime.now(),
                LocalDateTime.of(2021, 05, 10, 14, 0, 0)
        );

        Bid bid = new Bid(12.5);
        if(auction.addBid(bid)){
            bidRepository.save(bid);
        }

        auctionRepository.save(auction);
    }
}
