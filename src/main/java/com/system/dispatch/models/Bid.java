package com.system.dispatch.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double bidSum;

    public Bid() {}

    public Bid(Double bidSum) {
        this.bidSum = bidSum;
    }

    public Integer getId() {
        return id;
    }

    public Double getBidSum() {
        return bidSum;
    }

    public void setBidSum(Double bidSum) {
        this.bidSum = bidSum;
    }
}
