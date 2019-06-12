package com.example.finalui;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import static android.view.View.GONE;

public class AttendanceActivity extends AppCompatActivity implements
        VvCalendarView.EventHandler, VvVolleyInterface {

    private VvCalendarView vvCalendarView;
    private Button datepic, punchin, punchout;
    Calendar calendar;
    DatePickerDialog dialog, finpicker;
    HashSet<ObjectAttendance> eventList;
    int day, month, year;
    TextView textView, atte;
    Date sselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getSupportActionBar().setTitle("Attendance");
        datepic = findViewById(R.id.button);
        punchin = findViewById(R.id.button2);
        punchout = findViewById(R.id.button3);
        vvCalendarView = findViewById(R.id.calendervv);

        eventList = new HashSet<>();
        textView = findViewById(R.id.textView);
        atte = findViewById(R.id.attendance);


        if (ApplicationVariable.ACCOUNT_DATA.punchin == 2 && ApplicationVariable.ACCOUNT_DATA.punchout == 1) {
            Log.d("DSK_OPER", "PUNCHIN DONE PUNCHOUT LEFT");

            punchin.setVisibility(GONE);
        } else if (ApplicationVariable.ACCOUNT_DATA.punchin == 2 && ApplicationVariable.ACCOUNT_DATA.punchout == 2) {
            Log.d("DSK_OPER", "PUNCHOUT ALSO DONE!!");

            punchin.setVisibility(GONE);
            punchout.setVisibility(GONE);
        }


        //Required
        datepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int cday = calendar.get(Calendar.DAY_OF_MONTH);
                int cmonth = calendar.get(Calendar.MONTH);
                int cyear = calendar.get(Calendar.YEAR);

            }
        });
        //PunchIN
        punchin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("DSK_OPER", "RUNNING");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    eventList.add(new ObjectAttendance(simpleDateFormat.parse(day + "-" + month + "-" + year), true, false));
                } catch (Exception e) {
                    Log.d("DSK_OPER", e.toString());
                }
                vvCalendarView.setAttendance(eventList);
                punchin.setEnabled(false);
            }
        });

        punchout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    for (ObjectAttendance attendance : eventList) {
                        if (attendance.date.toString().equals(sselected.toString())) {
                            attendance.punchOut = true;
                        }
                    }
                    // eventList.add(new ObjectAttendance(simpleDateFormat.parse(day+"-"+month+"-"+year), true, true));
                } catch (Exception e) {
                    Log.d("DSK_OPER", e.toString());
                }
                vvCalendarView.setAttendance(eventList);
                atte.setText(R.string.attmark);
                punchout.setEnabled(false);
            }
        });

    }


    @Override
    public void onTaskComplete(String result) {

    }

    @Override
    public void onDayLongPress(Date date) {

    }

    @Override
    public void onDayPress(Date date) {

    }
}
