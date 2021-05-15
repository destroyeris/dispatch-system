package com.system.dispatch.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Double longitude;
    private Double latitude;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "first_place_id")
    private Set<Segment> firstLocationSegments;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "second_place_id")
    private Set<Segment> secondLocationSegments;

    public Place() {
    }

    public Place(Double longitude, Double latitude, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Segment> getFirstLocationSegments() {
        return firstLocationSegments;
    }

    public Set<Segment> getSecondLocationSegments() {
        return secondLocationSegments;
    }
}
