package com.system.dispatch.controllers;

import com.system.dispatch.models.*;
import com.system.dispatch.repositories.AuctionRepository;
import com.system.dispatch.repositories.BidRepository;
import com.system.dispatch.repositories.ItemRepository;
import com.system.dispatch.repositories.SoldItemRepository;
import com.system.dispatch.utils.SelectAuctionWinners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    private final Logger LOG = Logger.getLogger(SelectAuctionWinners.class.getName());


    @GetMapping("/auctions")
    public String getAuctions(Model model, HttpServletResponse response) {
        model.addAttribute("auctions", auctionRepository.findAllActive());
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

        double amount = Double.parseDouble(auction.getAmount());
        double price = Double.parseDouble(auction.getPrice());

        SoldItem soldItem = new SoldItem();
        Optional<Item> optItem = itemRepository.findById(Integer.parseInt(auction.getItemId()));
        if ((optItem.isPresent() && sDate.isBefore(eDate)) && (amount > 0 && price > 0)) {
            soldItem.setItem(optItem.get());
        } else {
            return openAuctionForm(model, response, true);
        }
        soldItem.setAmount(amount);
        soldItem.setPrice(price);

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

    @GetMapping("/bidForm")
    public String openBiddingForm(@RequestParam Integer auction, Model model, HttpServletResponse response, Boolean error) {
        Optional<Auction> optAuction = auctionRepository.findById(auction);
        if (optAuction.isPresent()) {
            Auction auc = optAuction.get();
            auc.sortBids();
            model.addAttribute("auction", auc);
            model.addAttribute("auctionForm", new AuctionForm());
            model.addAttribute("error", error != null && error ? "display: visible" : "display: none");
            return "auctions/bidForm";
        }
        return "auction/auctions";
    }

    @PostMapping("/bid")
    public String bid(@RequestParam Integer auction, @ModelAttribute AuctionForm auc, Model model, HttpServletResponse response) {
        Optional<Auction> optAuction = auctionRepository.findById(auction);
        if (optAuction.isEmpty()) return getAuctions(model, response);
        Auction auctionFromDb = optAuction.get();
        try {
            Double.parseDouble(auc.getPrice());
        } catch (Exception e) {
            return openBiddingForm(auction, model, response, true);
        }
        Bid newBid = new Bid(Double.parseDouble(auc.getPrice()));

        if (!auctionFromDb.addBid(newBid)) return openBiddingForm(auction, model, response, true);
        bidRepository.save(newBid);
        auctionRepository.save(auctionFromDb);
        return getAuctions(model, response);
    }

    public LocalDateTime parseStringTime(String dt) {
        return LocalDateTime.parse(dt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
