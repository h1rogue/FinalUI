package com.example.finalui;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.example.finalui.Activities.TaskActivity;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView emipd,empname,empdes;
    private Button emdet,punchin,punchout,report,compy;
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

        emipd=findViewById(R.id.textView5);
        empname=findViewById(R.id.textView6);
        empdes=findViewById(R.id.textView7);
        punchin=findViewById(R.id.button7);
        punchout=findViewById(R.id.button9);
        report=findViewById(R.id.button10);
        emdet=findViewById(R.id.button5);
        compy=findViewById(R.id.compdet);

        emdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,EmployeeDetActivity.class);
                startActivity(intent);
            }
        });


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,AttendanceActivity.class);
                startActivity(intent);
            }
        });

        compy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CompanyDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            Intent intent = new Intent(getApplicationContext(),AttendanceActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_rider_app) {
            //Rider App
        } else if (id == R.id.nav_all_rides) {
            //List of rides
        }else if(id == R.id.nav_salary){
           Intent intent = new Intent(getApplicationContext(),SalaryDetailsActivity.class);
           startActivity(intent);
        }else if(id == R.id.nav_taks_assigned){
           Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
           startActivity(intent);
        }else if(id == R.id.nav_employeeInfo){
            // Employee details
            Intent intent = new Intent(getApplicationContext(),EmployeeDetActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_comp_details) {
            Intent intent = new Intent(this,CompanyDetailActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this,CompanyContactActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
