package com.deskneed.team;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.deskneed.team.R;

public class CompanyContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_contact);
        getSupportActionBar().setTitle("Contacts");
    }
}
