package com.system.dispatch.utils;

import com.system.dispatch.models.Auction;
import com.system.dispatch.repositories.AuctionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class SelectAuctionWinners implements Runnable {
    private final Logger LOG = Logger.getLogger(SelectAuctionWinners.class.getName());

    private AuctionRepository auctionRepository;

    public SelectAuctionWinners(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    public void run() {
        Iterable<Auction> activeAuctions = auctionRepository.findAllActive();
        activeAuctions.forEach(auction -> {
                    if (auction.getEndTime().isBefore(LocalDateTime.now())) {
                        auction.setStatus("ENDED");
                        auctionRepository.save(auction);
                        LOG.info("Auction with id = " + auction.getId().toString() +
                                " has ended. It's status was changed to ENDED. Winner bid = " + auction.getHighestBid());

                    }
                }
        );
    }
}
