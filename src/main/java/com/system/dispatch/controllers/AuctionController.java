package com.system.dispatch.controllers;

import com.system.dispatch.models.Auction;
import com.system.dispatch.models.AuctionForm;
import com.system.dispatch.models.Item;
import com.system.dispatch.models.SoldItem;
import com.system.dispatch.repositories.AuctionRepository;
import com.system.dispatch.repositories.BidRepository;
import com.system.dispatch.repositories.ItemRepository;
import com.system.dispatch.repositories.SoldItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class AuctionController {
    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SoldItemRepository soldItemRepository;

    @Autowired
    private BidRepository bidRepository;

    private final Logger LOG = Logger.getLogger(AuctionController.class.getName());

    @GetMapping("/auctions")
    public String getAuctions(Model model, HttpServletResponse response) {
        model.addAttribute("auctions", auctionRepository.findAll());
        response.setStatus(HttpServletResponse.SC_OK);
        return "auctions/auctions";
    }

    @GetMapping("/auction/form")
    public String openAuctionForm(Model model, HttpServletResponse response, Boolean error) {
        model.addAttribute("title", "Add auction");
        model.addAttribute("error", error != null && error ? "display: visible" : "display: none");
        model.addAttribute("auction", new AuctionForm());
        model.addAttribute("items", itemRepository.findAll());
        response.setStatus(HttpServletResponse.SC_OK);
        return "auctions/auctionForm";
    }

    @PostMapping("/auction/create")
    public String createAuction(@ModelAttribute AuctionForm auction, Model model, HttpServletResponse response) {
        try {
            Integer.parseInt(auction.getItemId());
            Double.parseDouble(auction.getAmount());
            Double.parseDouble(auction.getPrice());
            parseStringTime(auction.getStartTime());
            parseStringTime(auction.getEndTime());
        } catch (Exception e) {
            return openAuctionForm(model, response, true);
        }
        LocalDateTime sDate = parseStringTime(auction.getStartTime());
        LocalDateTime eDate = parseStringTime(auction.getEndTime());

        SoldItem soldItem = new SoldItem();
        Optional<Item> optItem = itemRepository.findById(Integer.parseInt(auction.getItemId()));
        if (optItem.isPresent() && sDate.isBefore(eDate)) {
            soldItem.setItem(optItem.get());
        } else {
            return openAuctionForm(model, response, true);
        }
        soldItem.setAmount(Double.parseDouble(auction.getAmount()));
        soldItem.setPrice(Double.parseDouble(auction.getPrice()));

        Auction newAuction = new Auction(
                soldItem,
                sDate,
                eDate
        );
        response.setStatus(HttpServletResponse.SC_OK);
        soldItemRepository.save(soldItem);
        auctionRepository.save(newAuction);
        return getAuctions(model, response);
    }

    @GetMapping("/bid")
    public String openBiddingForm(@RequestParam Integer auction, Model model, HttpServletResponse response) {
        LOG.info("bid auction = " + auction);
//        TODO
        return "";
    }

    public LocalDateTime parseStringTime(String dt) {
        return LocalDateTime.parse(dt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
