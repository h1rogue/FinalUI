package com.example.finalui.RideTrack;

import java.io.Serializable;

public class TripData implements Serializable {
    public String id;
    public String name;
    public float distance;
    public String duration;

    public TripData() {
    }

    public TripData(String id, String name, float distance, String duration) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}