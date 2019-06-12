package com.example.finalui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;

import com.example.finalui.Activities.TaskActivity;
import com.example.finalui.RideTrack.Details;
import com.example.finalui.RideTrack.MapsActivity;
import com.example.finalui.RideTrack.Trips;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.view.View.GONE;
import static com.example.finalui.RideTrack.MapsActivity.c;
import static com.example.finalui.RideTrack.MapsActivity.c1;
import static com.example.finalui.RideTrack.MapsActivity.chronometer;
import static com.example.finalui.RideTrack.MapsActivity.durationTime;
import static com.example.finalui.RideTrack.MapsActivity.i;
import static com.example.finalui.RideTrack.MapsActivity.mRequestLocationUpdatesButton;
import static com.example.finalui.RideTrack.MapsActivity.mService;
import static com.example.finalui.RideTrack.MapsActivity.pause;
import static com.example.finalui.RideTrack.MapsActivity.ppp;
import static com.example.finalui.RideTrack.MapsActivity.running;
import static com.example.finalui.RideTrack.MapsActivity.temp;


//  /team/attendance/mark
// mark_type=1 or 0,phone,token,date,new_data_row

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, VvVolleyInterface {

    private TextView emipd, empname, empdes,markAttendance;
    private Button emdet, punchin, punchout, report, compy;
    private LinearLayout linearLayout;
    private ProgressDialog progressDialog;
    int pIN=0,pOUT=0;

    //attendance/get  --- filter-jsonObject of todaydate
    public static int constant = 0, closed = 0;
    public static boolean openOrClose;
    public static int vv = 0, cdd = 0;
    public static TextView dist;
    public static Chronometer dura;


    public static Button stop, pause1, resume, start1, trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        markAttendance=findViewById(R.id.markattend);
        emipd = findViewById(R.id.textView5);
        empname = findViewById(R.id.textView6);
        empdes = findViewById(R.id.textView7);
        punchin = findViewById(R.id.button7);
        punchout = findViewById(R.id.button9);
        report = findViewById(R.id.button10);
        emdet = findViewById(R.id.button5);
        compy = findViewById(R.id.compdet);
        linearLayout = findViewById(R.id.colourLin);
        emipd.setText(ApplicationVariable.ACCOUNT_DATA.emp_id);
        empname.setText(ApplicationVariable.ACCOUNT_DATA.name);
        empdes.setText(ApplicationVariable.ACCOUNT_DATA.role);
        punchin.setVisibility(View.VISIBLE);
        punchout.setVisibility(View.VISIBLE);
//OnCreate Attendance Details
        if (ApplicationVariable.ACCOUNT_DATA.punchin == 0 && ApplicationVariable.ACCOUNT_DATA.punchout == 0) {
            Log.d("DSK_OPER","FIRST TIME RUNNING...");
            getAttendanceDetails();
        } else if (ApplicationVariable.ACCOUNT_DATA.punchin == 2 && ApplicationVariable.ACCOUNT_DATA.punchout == 1) {
            Log.d("DSK_OPER","PUNCHIN DONE PUNCHOUT LEFT");
            linearLayout.setBackgroundColor(getResources().getColor(R.color.open_color));
            markAttendance.setText(R.string.punch_in);
            punchin.setVisibility(GONE);
        } else if (ApplicationVariable.ACCOUNT_DATA.punchin == 2 && ApplicationVariable.ACCOUNT_DATA.punchout == 2) {
            Log.d("DSK_OPER","PUNCHOUT ALSO DONE!!");
            markAttendance.setText(R.string.punch_out);
            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            punchin.setVisibility(GONE);
            punchout.setVisibility(GONE);
        }


        emdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, EmployeeDetActivity.class);
                startActivity(intent);
            }
        });


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AttendanceActivity.class);
                startActivity(intent);
            }
        });

        compy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CompanyDetailActivity.class);
                startActivity(intent);
            }
        });

        //gagan
        dist = findViewById(R.id.dist11);
        dura = findViewById(R.id.dura11);

        Log.i("kk", String.valueOf(c));
        if (c != 0 && c % 2 != 0) {
            dura.setBase(chronometer.getBase());
            dura.start();
        }
        start1 = findViewById(R.id.start1);
        pause1 = findViewById(R.id.pause);
        resume = findViewById(R.id.resume);
        stop = findViewById(R.id.stop);
        trips = findViewById(R.id.trips);
        pause1.setOnClickListener(view ->
        {
            c = 0;
            pause1.setVisibility(View.INVISIBLE);
            resume.setVisibility(View.VISIBLE);
            c1 = 1;
            mRequestLocationUpdatesButton.setText("RESUME");
            chronometer.setBase(dura.getBase());
            chronometer.stop();
            dura.stop();
            pause = SystemClock.elapsedRealtime() - chronometer.getBase();
            ppp = SystemClock.elapsedRealtime() - dura.getBase();
            running = false;
            durationTime = SystemClock.elapsedRealtime();
            mService.requestLocationUpdates();
        });

        resume.setOnClickListener(view ->
        {
            c = 1;
            c1 = 0;
            resume.setVisibility(View.INVISIBLE);
            pause1.setVisibility(View.VISIBLE);
            mRequestLocationUpdatesButton.setText("PAUSE");
            chronometer.setBase(SystemClock.elapsedRealtime() - pause);
            dura.setBase(SystemClock.elapsedRealtime() - ppp);
            chronometer.start();

            dura.start();
            running = true;
            mService.requestLocationUpdates();
        });

        stop.setOnClickListener(view ->
        {
            resume.setVisibility(View.VISIBLE);
            pause1.setVisibility(View.VISIBLE);
            start1.setVisibility(View.VISIBLE);
            c = -1;
            c1 = 0;
            Intent intent = new Intent(HomeActivity.this, Details.class);
            intent.putExtra("dist", temp);
            intent.putExtra("duration", dura.getText());
            startActivity(intent);
            MapsActivity.fa.finish();
        });
        start1.setOnClickListener(view ->
        {
            vv = 6;
            start1.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
            startActivity(intent);
        });
        trips.setOnClickListener(view -> {
            Intent i = new Intent(HomeActivity.this, Trips.class);
            startActivity(i);
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //moveTaskToBack(true);
            //finish();
//            i++;
            moveTaskToBack(true);
            super.onBackPressed();
        }
    }

    //gagan
    @Override
    protected void onResume() {
        openOrClose = true;
        if (vv == 6) {
            resume.setVisibility(View.VISIBLE);
            pause1.setVisibility(View.VISIBLE);
            //start1.setVisibility(View.INVISIBLE);
        }
        Log.i("resume", String.valueOf(openOrClose));
        super.onResume();
    }

    //gagan

    @Override
    protected void onPause() {
        openOrClose = false;

        Log.i("resume", String.valueOf(openOrClose));
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_attendance) {
            // Attendance Activity
            Intent intent = new Intent(getApplicationContext(), AttendanceActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_rider_app) {
            //Rider App
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_all_rides) {
            //List of rides
            Intent i = new Intent(getApplicationContext(), Trips.class);
            startActivity(i);
        } else if (id == R.id.nav_salary) {
            Intent intent = new Intent(getApplicationContext(), SalaryDetailsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_taks_assigned) {
            Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_employeeInfo) {
            // Employee details
            Intent intent = new Intent(getApplicationContext(), EmployeeDetActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_comp_details) {
            Intent intent = new Intent(this, CompanyDetailActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this, CompanyContactActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void RideAppOpen(View view) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void PunchInbutt(View view) {
        progressDialog.show();
        doPuchInrequest();
    }

    public void PunchOutbutt(View view) {
        progressDialog.show();
        doPunchOutrequest();
    }

    private void doPunchOutrequest() {
        pOUT=1;
        ApplicationVariable.ACCOUNT_DATA.punchout = 2;
        Log.d("DSK_OPER", "doPuchOutrequest");
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mark_type", 0);
        jsonObject.addProperty("lat", 0);
        jsonObject.addProperty("long", 0);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        jsonObject.addProperty("date", date);
        params.put("new_data_row", jsonObject.toString());
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.phone);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("reg_id", ApplicationVariable.ACCOUNT_DATA.reg_id);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/attendance/mark", params);
    }

    private void doPuchInrequest() {
        pIN=1;
        Log.d("DSK_OPER", "doPuchInrequest");
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mark_type", 1);
        jsonObject.addProperty("lat", 0);
        jsonObject.addProperty("long", 0);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        jsonObject.addProperty("date", date);
        params.put("new_data_row", jsonObject.toString());
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.phone);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("reg_id", ApplicationVariable.ACCOUNT_DATA.reg_id);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/attendance/mark", params);
    }

    private void getAttendanceDetails() {
        progressDialog.show();
        Log.d("DSK_OPER", "getAttendance Details");
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.phone);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("reg_id", ApplicationVariable.ACCOUNT_DATA.reg_id);
        JsonObject jsonObject = new JsonObject();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        jsonObject.addProperty("date", date);
        params.put("filter", jsonObject.toString());
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/attendance/get", params);
    }

    @Override
    public void onTaskComplete(String result) {
        Log.d("DSK_OPER", result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            //todo-get equals
            if (jsonObject.getString("responseFor").equals("team/attendance/get")) {
                Log.d("DSK_OPER", "getAttendance");
                afterCheckAttendance(jsonObject);
            } else if (jsonObject.getString("responseFor").equals("team/attendance/mark")) {
                afterButtonClick(jsonObject);
                Log.d("DSK_OPER", "Attendance after button");
                Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.dismiss();
                Log.d("DSK_OPER", "Not Matched Api");
                Toast.makeText(this, "Not Matched Api", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    private void afterButtonClick(JSONObject clickResponse) throws JSONException {
        Log.d("DSK_OPER", "afterButtonClicked");
        String timein=clickResponse.getJSONObject("data_row").getString("time_in");
        String timeout=clickResponse.getJSONObject("data_row").getString("time_out");
        if(timein.length()>0 && timeout.equals("null"))
        {
            progressDialog.dismiss();
            Log.d("DSK_OPER", "punCHin DONE");
            ApplicationVariable.ACCOUNT_DATA.punchin = 2;
            linearLayout.setBackgroundColor(getResources().getColor(R.color.open_color));
            punchin.setVisibility(GONE);
            markAttendance.setText(R.string.punch_in);
        }

        else if(timein.length()>0 && timeout.length()>0){
            progressDialog.dismiss();
            Log.d("DSK_OPER", "PUNCHOUT DONE");
            ApplicationVariable.ACCOUNT_DATA.punchout= 2;
            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            punchout.setVisibility(GONE);
            markAttendance.setText(R.string.punch_out);
        }
    }

    private void afterCheckAttendance(JSONObject jsonObject) throws JSONException {
        Log.d("DSK_OPER", "afterCheckedAttendance");
        String timeIn = "";
        String timeOut = "";

        if (jsonObject.getInt("data_rows_size") == 0) {
            Toast.makeText(this, "Give Attendance", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            ApplicationVariable.ACCOUNT_DATA.punchin = 1;
            ApplicationVariable.ACCOUNT_DATA.punchout = 1;
            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            markAttendance.setText(R.string.absent);
        } else {
            timeIn = jsonObject.getJSONArray("data_rows").getJSONObject(0).getString("time_in");
            timeOut = jsonObject.getJSONArray("data_rows").getJSONObject(0).getString("time_out");
            if (timeIn.length() > 0 && timeOut.equals("null")) {
                Toast.makeText(this, "PunchOut Not Done", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                ApplicationVariable.ACCOUNT_DATA.punchin = 2;
                ApplicationVariable.ACCOUNT_DATA.punchout = 1;
                punchin.setVisibility(GONE);
                linearLayout.setBackgroundColor(getResources().getColor(R.color.open_color));
                markAttendance.setText(R.string.punch_in);
            } else if (timeIn.length() > 0 && timeOut.length() > 0) {
                Toast.makeText(this, "Both PunchIn PunchOut Done", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                ApplicationVariable.ACCOUNT_DATA.punchin = 2;
                ApplicationVariable.ACCOUNT_DATA.punchout = 2;
                punchout.setVisibility(GONE);
                punchin.setVisibility(GONE);
                linearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                markAttendance.setText(R.string.punch_out);
            }

        }

    }
}
