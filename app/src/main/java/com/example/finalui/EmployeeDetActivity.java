package com.example.finalui;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EmployeeDetActivity extends AppCompatActivity {

    private Button button,changepass;
    public static final int REQUEST_CODE=123;
    private LinearLayout layout;
    private TextView eid,ename,eadd,eblood,dob,sal,contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_det);
        getSupportActionBar().setTitle("Employee Details");

        layout = findViewById(R.id.linearlay2);
        button = findViewById(R.id.button);

        eid=findViewById(R.id.emplyee_id);
        ename=findViewById(R.id.emplyee_name);
        eadd=findViewById(R.id.emplyee_address);
        eblood=findViewById(R.id.emplyee_bloodgrp);
        dob=findViewById(R.id.emplyee_dob);
        sal=findViewById(R.id.salary);
        contact=findViewById(R.id.emplyee_contact);
        changepass=findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeDetActivity.this,EditActivity.class);
                intent.putExtra("EID",eid.getText().toString().trim());
                intent.putExtra("ENAME",ename.getText().toString().trim());
                intent.putExtra("EADD",eadd.getText().toString().trim());
                intent.putExtra("ECON",contact.getText().toString().trim());
                intent.putExtra("EBLOOD",eblood.getText().toString().trim());
                intent.putExtra("DOB",dob.getText().toString().trim());
                startActivityForResult(intent,REQUEST_CODE);
            }
        });


        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeDetActivity.this,ChagePawwordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            eid.setText(data.getStringExtra("EID"));
            ename.setText(data.getStringExtra("ENAME"));
            eadd.setText(data.getStringExtra("EADD"));
            contact.setText(data.getStringExtra("ECON"));
            eblood.setText(data.getStringExtra("EBLOOD"));
            dob.setText(data.getStringExtra("DOB"));
        }
    }
}
