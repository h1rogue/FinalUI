package com.example.finalui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CompanyDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        getSupportActionBar().setTitle("Company details");
    }
}
