package com.system.dispatch.models;

import javax.persistence.*;

@Entity
public class Segment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="first_place_id")
    private Place firstPlace;

    @ManyToOne
    @JoinColumn(name="second_place_id")
    private Place secondPlace;

    @OneToOne
    private Obstacle obstacle;

    private Integer travelTime;

    public Segment() {
    }

    public Segment(Integer id, Place firstPlace, Place secondPlace, Integer travelTime, Obstacle obstacle) {
        this.id = id;
        this.firstPlace = firstPlace;
        this.secondPlace = secondPlace;
        this.travelTime = travelTime;
        this.obstacle = obstacle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Place getFirstPlace() {
        return firstPlace;
    }

    public void setFirstPlace(Place firsPlace) {
        this.firstPlace = firsPlace;
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

    public Obstacle getObstacle() {
        return this.obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }
}
