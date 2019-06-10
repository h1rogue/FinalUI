package com.example.finalui.RideTrack;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class RideDetailClass {
    String tripname;
    String duration;
    float avgspeed;
    float distance;
    List<LatLng> track;

    public RideDetailClass(String tripname, String duration, float avgspeed, float distance, List<LatLng> track) {
        this.tripname = tripname;
        this.duration = duration;
        this.avgspeed = avgspeed;
        this.distance = distance;
        this.track = track;
    }

    public String getTripname() {
        return tripname;
    }

    public void setTripname(String tripname) {
        this.tripname = tripname;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public float getAvgspeed() {
        return avgspeed;
    }

    public void setAvgspeed(float avgspeed) {
        this.avgspeed = avgspeed;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public List<LatLng> getTrack() {
        return track;
    }

    public void setTrack(List<LatLng> track) {
        this.track = track;
    }
}
