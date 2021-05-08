package com.system.dispatch.models;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double highestBid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @OneToMany
    private List<Bid> bids;
    @OneToOne
    private SoldItem soldItem;

    public Auction() {}

    public Auction(SoldItem item, LocalDateTime startTime, LocalDateTime endTime) {
        this.highestBid = item.getPrice();
        this.startTime = startTime;
        this.endTime = endTime;
        this.bids = new ArrayList<Bid>();
        this.soldItem = item;
    }

    public void sortBids(){
        bids.sort((a, b) -> (int) (b.getBidSum() - a.getBidSum()));
    }

    public Integer getId() {
        return id;
    }

    public Double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Double highestBid) {
        this.highestBid = highestBid;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public boolean addBid(Bid bid) {
        if(bid != null && bid.getBidSum() > this.highestBid){
            this.highestBid = bid.getBidSum();
            this.bids.add(bid);
            return true;
        }
        return false;
    }

    public SoldItem getSoldItem() {
        return soldItem;
    }

    public void setSoldItem(SoldItem item) {
        this.soldItem = item;
    }

    public String parseDateTime(LocalDateTime dt){
        return dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}