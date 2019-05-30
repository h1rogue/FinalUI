package com.example.finalui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CompanyContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_contact);
        getSupportActionBar().setTitle("Contacts");
    }
}
