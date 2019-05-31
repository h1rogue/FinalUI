package com.example.finalui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    private EditText editID,editName,editAdd,editCOn,editblood,editDOB;
    private Button savebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setTitle("Edit");
        
        //Declare all edit text

        savebutton=findViewById(R.id.savebutton);
        editID=findViewById(R.id.emplyee_edit_id);
        editName=findViewById(R.id.emplyee_editname);
        editAdd=findViewById(R.id.emplyee_editadd);
        editCOn=findViewById(R.id.emplyee_editcontact);
        editblood=findViewById(R.id.emplyee_editbloodgrp);
        editDOB=findViewById(R.id.emplyee_editdob);
        //Get the Intents
        Intent intent = getIntent();
        String eid = intent.getStringExtra("EID");
        String ename= intent.getStringExtra("ENAME");
        String eadd = intent.getStringExtra("EADD");
        String econ = intent.getStringExtra("ECON");
        String eblood = intent.getStringExtra("EBLOOD");
        String edob = intent.getStringExtra("DOB");
        String sal = intent.getStringExtra("SAL");


        //Add All th Values
        editID.setText(eid);
        editName.setText(ename);
        editAdd.setText(eadd);
        editCOn.setText(econ);
        editblood.setText(eblood);
        editDOB.setText(edob);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("EID", editID.getText().toString().trim());
                intent1.putExtra("ENAME",editName.getText().toString().trim());
                intent1.putExtra("EADD",editAdd.getText().toString().trim());
                intent1.putExtra("ECON", editCOn.getText().toString().trim());
                intent1.putExtra("EBLOOD", editblood.getText().toString().trim());
                intent1.putExtra("DOB",editDOB.getText().toString().trim());
                setResult(RESULT_OK,intent1);
                finish();
            }
        });
    }
}
