package com.example.finalui;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        changepass = findViewById(R.id.button4);

        gettheData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeDetActivity.this, EditActivity.class);
                intent.putExtra("EID", emp_id.getText().toString().trim());
                intent.putExtra("ENAME", ename.getText().toString().trim());
                intent.putExtra("EADD", eadd.getText().toString().trim());
                intent.putExtra("ECON", contact.getText().toString().trim());
                intent.putExtra("EBLOOD", blood_group.getText().toString().trim());
                intent.putExtra("DOB", dob.getText().toString().trim());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeDetActivity.this, ChagePawwordActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            emp_id.setText(data.getStringExtra("emp_id"));
            ename.setText(data.getStringExtra("ENAME"));
            eadd.setText(data.getStringExtra("EADD"));
            contact.setText(data.getStringExtra("ECON"));
            blood_group.setText(data.getStringExtra("EBLOOD"));
            dob.setText(data.getStringExtra("DOB"));
        }
    }
    private void gettheData() {
        emp_id.setText(ApplicationVariable.ACCOUNT_DATA.emp_id);
        ename.setText(ApplicationVariable.ACCOUNT_DATA.name);
        eadd.setText(ApplicationVariable.ACCOUNT_DATA.address);
        contact.setText(ApplicationVariable.ACCOUNT_DATA.contact);
        blood_group.setText(ApplicationVariable.ACCOUNT_DATA.blood_group);
        dob.setText(ApplicationVariable.ACCOUNT_DATA.dob);
        sal.setText(ApplicationVariable.ACCOUNT_DATA.salary);
    }
}