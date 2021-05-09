package com.system.dispatch.bootstrap;

import com.system.dispatch.models.*;
import com.system.dispatch.repositories.*;
import com.system.dispatch.utils.SelectAuctionWinners;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final ItemRepository productRepository;
    private final PlaceRepository placeRepository;
    private final SegmentRepository segmentRepository;
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final SoldItemRepository soldItemRepository;

    public DevBootstrap(ItemRepository productRepository,
                        PlaceRepository placeRepository,
                        SegmentRepository segmentRepository,
                        AuctionRepository auctionRepository,
                        BidRepository bidRepository,
                        SoldItemRepository soldItemRepository
    ) {
        this.placeRepository = placeRepository;
        this.productRepository = productRepository;
        this.segmentRepository = segmentRepository;
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
        this.soldItemRepository = soldItemRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
            exec.scheduleAtFixedRate(new SelectAuctionWinners(auctionRepository), 10, 5 * 60, TimeUnit.SECONDS);
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        initAuctions();
        initSegments(initLocations());

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

    private void initSegments(List<Place> places) {
        Segment segment = new Segment();

        segment.setFirsPlace(places.get(0));
        segment.setSecondPlace(places.get(2));
        segment.setTravelTime(24);

        segmentRepository.save(segment);

        segment = new Segment();

        segment.setFirsPlace(places.get(0));
        segment.setSecondPlace(places.get(1));
        segment.setTravelTime(11);

        segmentRepository.save(segment);

        segment = new Segment();

        segment.setFirsPlace(places.get(1));
        segment.setSecondPlace(places.get(2));
        segment.setTravelTime(6);

        segmentRepository.save(segment);
    }

    private void initAuctions() {
        Item item1 = new Item(null, "Item1", 15.0, "kg", 9.5, "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/c496dc3199003.5acdbd673bd52.jpg", "UK");
        productRepository.save(item1);
        SoldItem soldItem = new SoldItem(10.0, 1.0, item1);
        soldItemRepository.save(soldItem);

        Auction auction = new Auction(
                soldItem,
                LocalDateTime.now(),
                LocalDateTime.now().plusSeconds(60)
        );

        Bid bid = new Bid(12.5);
        if (auction.addBid(bid)) {
            bidRepository.save(bid);
        }

        auctionRepository.save(auction);
    }

    private List<Place> initLocations() {
        List<Place> places = new ArrayList<>();
        Place place = new Place();

        place.setName("A");
        place.setLongitude(14.5);
        place.setLatitude(13.5);

        placeRepository.save(place);
        places.add(place);

        place = new Place();

        place.setName("B");
        place.setLongitude(12.45);
        place.setLatitude(16.75);

        placeRepository.save(place);
        places.add(place);

        place = new Place();

        place.setName("C");
        place.setLongitude(14.5);
        place.setLatitude(13.5);

        placeRepository.save(place);
        places.add(place);

        place = new Place();

        place.setName("D");
        place.setLongitude(14.5);
        place.setLatitude(27.5);

        placeRepository.save(place);
        places.add(place);

        return places;
    }
}
