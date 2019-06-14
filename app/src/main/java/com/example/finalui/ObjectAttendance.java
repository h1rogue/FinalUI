package com.example.finalui;

import java.util.Date;

public class ObjectAttendance {
    Date date;
    Boolean punchIn = false;
    Boolean punchOut = false;
    String time_in,time_out,lat_in,long_in,lat_out,long_out;

    ObjectAttendance(Date date, Boolean punchIn, Boolean punchOut,
                     String time_in,String time_out,String lat_in,String lat_out,
                    String long_in,String long_out ){
        this.date = date;
        this.punchIn = punchIn;
        this.punchOut = punchOut;
        this.time_in = time_in;
        this.time_out = time_out;
        this.lat_in = lat_in;
        this.long_in = long_in;
        this.lat_out = lat_out;
        this.long_out = long_out;
    }
    ObjectAttendance(){}
}
