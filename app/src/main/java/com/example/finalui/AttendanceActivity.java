package com.example.finalui;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import static android.view.View.GONE;
import static com.example.finalui.HomeActivity.hong;
import static com.example.finalui.HomeActivity.kong;

public class AttendanceActivity extends AppCompatActivity implements VvVolleyInterface {

    ProgressDialog progressDialog;
    VvCalendarView vvCalendarView;
    Button datepic, punchin, punchout;
    Calendar calendar;
    HashSet<ObjectAttendance> eventList;
    // Map<String, EachDateDetail> hasmapofdates;
    int day, month, year;
    TextView textView, timeIN, timeOut, latitudeIn, longitudeIN, latitudeOut, longitudeOut;
    LinearLayout linear;
    Date sselected;

    boolean t = false;

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

        timeIN = findViewById(R.id.timeIn);
        timeOut = findViewById(R.id.timeOut);

        latitudeIn = findViewById(R.id.timelatitudeIN);
        longitudeIN = findViewById(R.id.timelongitudeIN);
        latitudeOut = findViewById(R.id.timelatitudeout);
        longitudeOut = findViewById(R.id.timelongitudeout);

        linear = findViewById(R.id.linearlay45);
        // hasmapofdates = new HashMap<>();
        sselected = new Date();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);

        getAlldates();

        vvCalendarView.setEventHandler(new VvCalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date) {

            }

            @Override
            public void onDayPress(Date date) {
                timeIN.setText("");
                timeOut.setText("");
                latitudeIn.setText("");
                latitudeOut.setText("");
                longitudeIN.setText("");
                longitudeOut.setText("");
                calendarDayClicked(date);
            }
        });

    }

    private void calendarDayClicked(Date Dates) {
        int flag = 0;
        punchin.setVisibility(View.GONE);
        punchout.setVisibility(GONE);

        SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd");
        String clicked = objSDF.format(Dates);

        ObjectAttendance obj = new ObjectAttendance();
        for (ObjectAttendance o : eventList) {
            String gotten = objSDF.format(o.date);
            if (clicked.equals(gotten)) {
                obj = o;
                flag = 1;
                break;
            }
        }
        String todaysdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (flag == 1 || todaysdate.equals(clicked)) {
            sselected = Dates;
            textView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Dates));

            timeIN.setText(obj.time_in);
            timeOut.setText(obj.time_out);
            latitudeIn.setText(obj.lat_in);
            latitudeOut.setText(obj.lat_out);
            longitudeIN.setText(obj.long_in);
            longitudeOut.setText(obj.long_out);


            Log.d("jsondatedate", "selecteddate" + clicked);
            if (obj.punchIn && !obj.punchOut) {
                punchin.setVisibility(GONE);
                punchout.setVisibility(View.VISIBLE);
            } else if (obj.punchIn && obj.punchOut) {
                punchin.setVisibility(GONE);
                punchout.setVisibility(GONE);
            } else {
                punchin.setVisibility(View.VISIBLE);
                punchout.setVisibility(GONE);
            }
        } else {
            Snackbar.make(linear, "Cannot set attendance for absent dates", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private void getAlldates() {
        progressDialog.show();
        VvVolleyClass vvVolleyClass = new VvVolleyClass(AttendanceActivity.this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.phone);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("reg_id", ApplicationVariable.ACCOUNT_DATA.reg_id);
        JsonObject jsonObject = new JsonObject();
        JsonObject filter = new JsonObject();
        int firstdate = 01;
        int lastdate = checkMonth();
        Date firstday, lastday;
        try {

            firstday = new SimpleDateFormat("yyyy-MM-dd").parse(
                    Calendar.getInstance().get(Calendar.YEAR) + "-" + Calendar.getInstance().get(Calendar.MONTH)
                            + "-" + firstdate);
            lastday = new SimpleDateFormat("yyyy-MM-dd").parse(Calendar.getInstance().get(Calendar.YEAR)
                    + "-" + Calendar.getInstance().get(Calendar.MONTH) + "-" + lastdate);
            String[] range = {firstday.toString(), lastday.toString()};
            jsonObject.addProperty("date", range.toString());
            params.put("range", jsonObject.toString());
            params.put("filter", filter.toString());
            vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/attendance/get", params);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private int checkMonth() {
        java.util.Date date = new Date();
        Log.d("QWERTY", date.toString());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        month++;
        Log.d("QWERTY", String.valueOf(month));
        switch (month) {
            case 1:
                return 31;

            case 2:
                return 28;

            case 3:
                return 31;

            case 4:
                return 30;

            case 5:
                return 31;

            case 6:
                return 30;

            case 7:
                return 31;

            case 8:
                return 31;

            case 9:
                return 30;

            case 10:
                return 31;

            case 11:
                return 30;

            case 12:
                return 31;

        }
        return 0;
    }

    public void PunchINButtClick(View view) {
        doPuchInrequest();
    }


    private void doPuchInrequest() {
        progressDialog.show();
        Log.d("DSK_OPER", "doPuchInrequest");
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mark_type", 1);
        jsonObject.addProperty("lat", 0);
        jsonObject.addProperty("long", 0);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(sselected);
        jsonObject.addProperty("date", date);
        params.put("new_data_row", jsonObject.toString());
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.phone);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("reg_id", ApplicationVariable.ACCOUNT_DATA.reg_id);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/attendance/mark", params);
    }


    public void PunchOUTButtClick(View view) {
        doPunchOutrequest();
    }


    private void doPunchOutrequest() {
        progressDialog.show();
        ApplicationVariable.ACCOUNT_DATA.punchout = 2;
        Log.d("DSK_OPER", "doPuchOutrequest");
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mark_type", 0);
        jsonObject.addProperty("lat", 0);
        jsonObject.addProperty("long", 0);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(sselected);
        jsonObject.addProperty("date", date);
        params.put("new_data_row", jsonObject.toString());
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.phone);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("reg_id", ApplicationVariable.ACCOUNT_DATA.reg_id);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/attendance/mark", params);
    }


    @Override
    public void onTaskComplete(String result) {
        Log.d("DSK_OPER", result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            //todo-get equals
            if (jsonObject.getString("responseFor").equals("team/attendance/get")) {
                getAttendanceList(jsonObject);
            } else if (jsonObject.getString("responseFor").equals("team/attendance/mark")) {
                getAlldates();
            } else {
                Log.d("DSK_OPER", "Not Matched Api");
                Toast.makeText(this, "Not Matched Api", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    private void getAttendanceList(JSONObject jsonObject) throws JSONException {
        progressDialog.dismiss();
        eventList = new HashSet<>();
        String timeIn = "";
        String timeOut = "";
        int data_rows_size = jsonObject.getInt("data_rows_size");
        if (data_rows_size == 0) {
            Toast.makeText(this, "Not Present for this month", Toast.LENGTH_SHORT).show();
        } else {
            JSONArray jsonArray = jsonObject.getJSONArray("data_rows");
            for (int i = 0; i < jsonArray.length(); i++) {

                timeIn = jsonArray.getJSONObject(i).getString("time_in");
                timeOut = jsonArray.getJSONObject(i).getString("time_out");
                String date = jsonArray.getJSONObject(i).getString("date");
                Log.d("jsondate", "jsondate" + date);

//                hasmapofdates.put(date, new EachDateDetail(timeIn, timeOut,
//                        jsonArray.getJSONObject(i).getString("lat_in"),
//                        jsonArray.getJSONObject(i).getString("long_in"),
//                        jsonArray.getJSONObject(i).getString("lat_out"),
//                        jsonArray.getJSONObject(i).getString("log_out")));

                Log.d("DSK_OPER", date);
                if (!timeIn.equals("null") && timeOut.equals("null")) {
                    Toast.makeText(this, "PunchOut Not Done", Toast.LENGTH_LONG).show();
                    try {
                        eventList.add(
                                new ObjectAttendance(new SimpleDateFormat("yyyy-MM-dd").parse(date),
                                        true,
                                        false, timeIn, timeOut, jsonArray.getJSONObject(i).getString("lat_in"),
                                        jsonArray.getJSONObject(i).getString("lat_out"),
                                        jsonArray.getJSONObject(i).getString("long_in"),
                                        jsonArray.getJSONObject(i).getString("lat_out")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else if (!timeIn.equals("null") && !timeOut.equals("null")) {
                    Toast.makeText(this, "Both PunchIn PunchOut Done", Toast.LENGTH_LONG).show();
                    try {
                        eventList.add(new ObjectAttendance(new SimpleDateFormat("yyyy-MM-dd").parse(date), true, true,
                                timeIn, timeOut, jsonArray.getJSONObject(i).getString("lat_in"),
                                jsonArray.getJSONObject(i).getString("lat_out"),
                                jsonArray.getJSONObject(i).getString("long_in"),
                                jsonArray.getJSONObject(i).getString("lat_out")  ));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        eventList.add(new ObjectAttendance(new SimpleDateFormat("yyyy-MM-dd").parse(date), false, false,
                                timeIn, timeOut, jsonArray.getJSONObject(i).getString("lat_in"),
                                jsonArray.getJSONObject(i).getString("lat_out"),
                                jsonArray.getJSONObject(i).getString("long_in"),
                                jsonArray.getJSONObject(i).getString("lat_out")     ));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            vvCalendarView.setAttendance(eventList);
            calendarDayClicked(sselected);
        }
    }
}
