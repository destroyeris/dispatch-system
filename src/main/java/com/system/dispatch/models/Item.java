package com.system.dispatch.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private Double amount;

    private String unitOfMeasurement;

    private Double rating;

    private String picture;

    private String country;

    public Item(Integer id, String name, Double amount, String unitOfMeasurement, Double rating, String picture, String country) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.unitOfMeasurement = unitOfMeasurement;
        this.rating = rating;
        this.picture = picture;
        this.country = country;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double review) {
        this.rating = review;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
