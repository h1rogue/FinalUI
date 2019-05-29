package com.example.finalui;

import java.util.Date;

public class ObjectAttendance {
    Date date;
    Boolean punchIn = false;
    Boolean punchOut = false;

    ObjectAttendance(Date date, Boolean punchIn, Boolean punchOut){
        this.date = date;
        this.punchIn = punchIn;
        this.punchOut = punchOut;
    }
}
