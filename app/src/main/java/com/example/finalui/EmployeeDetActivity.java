package com.example.finalui;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EmployeeDetActivity extends AppCompatActivity{

    private Button button, changepass;
    public static final int REQUEST_CODE = 123;
    private LinearLayout layout;
    private TextView emp_id, ename, eadd, blood_group, dob, sal, contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_det);
        getSupportActionBar().setTitle("Employee Details");

        layout = findViewById(R.id.linearlay2);
        button = findViewById(R.id.button);

        emp_id = findViewById(R.id.emplyee_id);
        ename = findViewById(R.id.emplyee_name);
        eadd = findViewById(R.id.emplyee_address);
        blood_group = findViewById(R.id.emplyee_bloodgrp);
        dob = findViewById(R.id.emplyee_dob);
        sal = findViewById(R.id.salary);
        contact = findViewById(R.id.emplyee_contact);
  //      changepass = findViewById(R.id.button4);

        gettheData();

    }

    private void gettheData() {
        emp_id.setText(ApplicationVariable.ACCOUNT_DATA.emp_id);
        ename.setText(ApplicationVariable.ACCOUNT_DATA.name);
        eadd.setText(ApplicationVariable.ACCOUNT_DATA.address);
        contact.setText(ApplicationVariable.ACCOUNT_DATA.contact);
        blood_group.setText(ApplicationVariable.ACCOUNT_DATA.blood_group);
        dob.setText("DOB: "+ApplicationVariable.ACCOUNT_DATA.dob);
        sal.setText("Salary: "+ApplicationVariable.ACCOUNT_DATA.salary);
    }

}