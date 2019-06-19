package com.deskneed.team;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.deskneed.team.R;

public class CompanyDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        getSupportActionBar().setTitle("Company details");
    }

}
