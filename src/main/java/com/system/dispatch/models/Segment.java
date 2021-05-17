package com.system.dispatch.models;

import javax.persistence.*;

@Entity
public class Segment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="first_location_id")
    private Location firstLocation;

    @ManyToOne
    @JoinColumn(name="second_location_id")
    private Location secondLocation;

    @OneToOne
    private Obstacle obstacle;

    private Integer travelTime;

    public Segment() {
    }

    public Segment(Integer id, Location firstLocation, Location secondLocation, Integer travelTime, Obstacle obstacle) {
        this.id = id;
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
        this.travelTime = travelTime;
        this.obstacle = obstacle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(Location firstLocation) {
        this.firstLocation = firstLocation;
    }

    public Location getSecondLocation() {
        return secondLocation;
    }

    public void setSecondLocation(Location secondLocation) {
        this.secondLocation = secondLocation;
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
