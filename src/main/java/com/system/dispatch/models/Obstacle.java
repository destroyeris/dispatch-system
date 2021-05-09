package com.system.dispatch.models;

import javax.persistence.*;

@Entity
public class Obstacle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long expirationTime;

    private String description;

    @OneToOne
    private Segment segment;

    public Obstacle() {
    }

    public Obstacle(Long expirationTime, String description, Segment segment) {
        this.expirationTime = expirationTime;
        this.description = description;
        this.segment = segment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
