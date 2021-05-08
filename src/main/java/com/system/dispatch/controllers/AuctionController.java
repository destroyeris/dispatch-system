package com.system.dispatch.controllers;

import com.system.dispatch.models.Auction;
import com.system.dispatch.models.Bid;
import com.system.dispatch.repositories.AuctionRepository;
import com.system.dispatch.repositories.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class AuctionController {
    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    private final Logger LOG = Logger.getLogger(AuctionController.class.getName());

    @GetMapping("/auctions")
    public String getAuctions(Model model, HttpServletResponse response){
        model.addAttribute("auctions", auctionRepository.findAll());
        response.setStatus(HttpServletResponse.SC_OK);
        return "auctions/auctions";
    }

    @GetMapping("/auction/create")
    public String createAuction(Model model, HttpServletResponse response){
        LOG.info("create auction called");
//        TODO
        return "";
    }

    @GetMapping("/bid")
    public String openBiddingForm(@RequestParam Integer auction, Model model, HttpServletResponse response){
        LOG.info("bid auction = " + auction);
//        TODO
        return "";
    }

}
