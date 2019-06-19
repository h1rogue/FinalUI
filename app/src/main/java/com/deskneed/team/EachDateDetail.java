package com.deskneed.team;

public class EachDateDetail {
    String timein;
    String timeout;
    String latitudeIN;
    String longitudeIN;
    String latitudeOUT;
    String longitudeOUT;

    public EachDateDetail(String timein, String timeout, String latitudeIN, String longitudeIN,String latitudeOUT,String longitudeOUT) {
        this.timein = timein;
        this.timeout = timeout;
        this.latitudeIN = latitudeIN;
        this.longitudeIN = longitudeIN;
        this.latitudeOUT = latitudeOUT;
        this.longitudeOUT = longitudeOUT;
    }

    public String getTimein() {
        return timein;
    }

    public void setTimein(String timein) {
        this.timein = timein;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getlatitudeIN() {
        return latitudeIN;
    }
    public String getlatitudeOUT() {
        return latitudeOUT;
    }
    public String getLongitudeOUT() {
        return longitudeOUT;
    }

    public void setlatitudeIN(String latitudeIN) {
        this.latitudeIN = latitudeIN;
    }

    public String getlongitudeIN() {
        return longitudeIN;
    }

    public void setlongitudeIN(String longitudeIN) {
        this.longitudeIN = longitudeIN;
    }
}
