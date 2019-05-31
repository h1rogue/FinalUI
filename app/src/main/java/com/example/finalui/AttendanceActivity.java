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

public class AttendanceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    private VvCalendarView vvCalendarView;
    private Button datepic,punchin,punchout;
    Calendar calendar;
    DatePickerDialog dialog,finpicker;
    HashSet<ObjectAttendance> eventList ;
    int day,month,year;
    TextView textView,atte;
    Date sselected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getSupportActionBar().setTitle("Attendance");
        datepic=findViewById(R.id.button);
        punchin=findViewById(R.id.button2);
        punchout=findViewById(R.id.button3);
        vvCalendarView=findViewById(R.id.calendervv);

        eventList = new HashSet<>();
        textView=findViewById(R.id.textView);
        atte=findViewById(R.id.attendance);

        datepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int cday = calendar.get(Calendar.DAY_OF_MONTH);
                int cmonth = calendar.get(Calendar.MONTH);
                int cyear = calendar.get(Calendar.YEAR);

                dialog  = DatePickerDialog.newInstance(AttendanceActivity.this,cyear,cmonth,cday);
                dialog.show(getSupportFragmentManager(),"datepickerID");
            }
        });

        punchin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("DSK_OPER", "RUNNING");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    eventList.add(new ObjectAttendance(simpleDateFormat.parse(day+"-"+month+"-"+year), true, false));
                }
                catch (Exception e){
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

                    for(ObjectAttendance attendance : eventList){
                        if(attendance.date.toString().equals(sselected.toString())){
                            attendance.punchOut=true;
                        }
                    }
                    // eventList.add(new ObjectAttendance(simpleDateFormat.parse(day+"-"+month+"-"+year), true, true));
                }
                catch (Exception e){
                    Log.d("DSK_OPER", e.toString());
                }
                vvCalendarView.setAttendance(eventList);
                atte.setText(R.string.attmark);
                punchout.setEnabled(false);
            }
        });


        finpicker = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag("datepickerID");
        if(finpicker!=null) finpicker.setOnDateSetListener(this);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int years, int monthOfYear, int dayOfMonth) {
        day=dayOfMonth;
        month=monthOfYear+1;
        year=years;
        textView.setText(day+"/"+month+"/"+year);
        punchin.setEnabled(true);
        punchout.setEnabled(true);
        showUpdateorNot(year,month,day);
    }


    private void showUpdateorNot(int year,int month,int day) {

        punchin.setVisibility(View.VISIBLE);
        punchout.setVisibility(View.GONE);
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            sselected = simpleDateFormat.parse(day+"-"+month+"-"+year);

            Log.d("DSK_OPER","sselected date"+sselected);

            for(ObjectAttendance attendance :eventList){
                Log.d("DSK_OPER","attendace date"+attendance.date.toString());
                if(attendance.date.toString().equals(sselected.toString())){
                    if(!attendance.punchIn){
                        Log.d("DSK_OPER","punchINWorking");
                        punchin.setVisibility(View.VISIBLE);
                        punchout.setVisibility(View.GONE);
                        atte.setText(R.string.punch_in);
                    }
                    if(attendance.punchOut && attendance.punchIn){
                        Log.d("DSK_OPER","Both Working");
                        punchin.setVisibility(View.GONE);
                        punchout.setVisibility(View.GONE);
                        atte.setText(R.string.attmark);
                    }
                    if(attendance.punchIn && !attendance.punchOut){
                        punchin.setVisibility(View.GONE);
                        punchout.setVisibility(View.VISIBLE);
                        atte.setText(R.string.punch_out);

                    }
                    if(attendance.punchOut && !attendance.punchIn){
                        punchin.setVisibility(View.GONE);
                        punchout.setVisibility(View.GONE);
                        atte.setText("INVALID DATA");
                    }
                    break;
                }
                else
                {
                    //Toast.makeText(MainActivity.this, "Date Not Present in the Attendance", Toast.LENGTH_LONG).show();
                    atte.setText("");
                }
            }
        }
        catch (Exception e){
            Log.d("DSK_OPER",e.toString());
        }
    }
}
