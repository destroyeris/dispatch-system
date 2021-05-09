package com.system.dispatch.models;

import javax.persistence.*;

@Entity
public class Segment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="first_place_id")
    private Place firsPlace;

    @ManyToOne
    @JoinColumn(name="second_place_id")
    private Place secondPlace;

    private Integer travelTime;

    public Segment() {
    }

    public Segment(Integer id, Place firsPlace, Place secondPlace, Integer travelTime) {
        this.id = id;
        this.firsPlace = firsPlace;
        this.secondPlace = secondPlace;
        this.travelTime = travelTime;
    }

    public Place getFirsPlace() {
        return firsPlace;
    }

    public void setFirsPlace(Place firsPlace) {
        this.firsPlace = firsPlace;
    }

    public Place getSecondPlace() {
        return secondPlace;
    }

    public void setSecondPlace(Place secondPlace) {
        this.secondPlace = secondPlace;
    }

    public Integer getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Integer travelTime) {
        this.travelTime = travelTime;
    }
}
