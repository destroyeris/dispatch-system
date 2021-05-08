package com.system.dispatch.models;

import javax.persistence.*;

@Entity
public class SoldItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double price;
    private Double amount;
    @ManyToOne
    private Item item;

    public SoldItem(Double price, Double amount, Item item) {
        this.price = price;
        this.amount = amount;
        this.item = item;
    }

    public Integer getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
