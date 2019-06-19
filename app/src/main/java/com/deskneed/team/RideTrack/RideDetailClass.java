package com.deskneed.team.RideTrack;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class RideDetailClass {
    String start_id;
    String end_id;
    String tripname;
    float duration;
    float avgspeed;
    float distance;
    List<LatLng> track;

    public RideDetailClass(String start_id, String end_id, String tripname, float duration, float avgspeed, float distance, List<LatLng> track) {
        this.tripname = tripname;
        this.duration = duration;
        this.avgspeed = avgspeed;
        this.distance = distance;
        this.start_id = start_id;
        this.end_id = end_id;
        this.track = track;
    }

    public String getTripname() {
        return tripname;
    }

    public void setTripname(String tripname) {
        this.tripname = tripname;
    }

    public void setEnd_id(String end_id) {
        this.end_id = end_id;
    }

    public String getEnd_id() {
        return end_id;
    }

    public String getStart_id() {
        return start_id;
    }

    public void setStart_id(String start_id) {
        this.start_id = start_id;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
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
