package com.system.dispatch.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Obstacle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime expirationTime;

    private String description;

    public Obstacle() {
    }

    public Obstacle(LocalDateTime expirationTime, String description) {
        this.expirationTime = expirationTime;
        this.description = description;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }
}
