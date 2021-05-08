package com.system.dispatch.models;

public class AuctionForm {
    private String itemId;
    private String amount;
    private String price;
    private String startTime;
    private String endTime;

    public AuctionForm() {}

    public AuctionForm(String itemId, String amount, String price, String startTime, String endTime) {
        this.itemId = itemId;
        this.amount = amount;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "AuctionForm{" +
                "itemId='" + itemId + '\'' +
                ", amount='" + amount + '\'' +
                ", price='" + price + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
