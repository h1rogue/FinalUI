package com.example.finalui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalui.Models.UpdateModel;
import com.example.finalui.R;
import com.example.finalui.VvVolleyInterface;
import com.example.finalui.fragments.itemupdatefragment;

public class AddUpdateActivity extends AppCompatActivity implements VvVolleyInterface, AdapterView.OnItemSelectedListener{

    boolean nextStatus;
    private EditText id,nextupdatedate,updateby;
    private TextView slipno,updatedate;
    private Button button;
    Spinner status,department,employee;
    String field;
    String[] statuses = new String[]{"INITIATED","FOLLOW","SITE_VISIT","QUOTATION","QUOTATION_FOLLOW",
                        "WORK_DUE","WORK_PROGRESS","INVOICE","PAYMENT","FEEDBACK"};

    String[] depts = new String[]{"ADMINISTRATION","MARKETING","OPERATIONS","ACCOUNTS","SALES",
            "GRAPHICS","MISC","TECHNOLOGY"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);
        id= findViewById(R.id.editText2);
        slipno=findViewById(R.id.editText4);
        updatedate=findViewById(R.id.editText5);
        status=findViewById(R.id.spinStatus);
        nextupdatedate=findViewById(R.id.editText7);
        department=findViewById(R.id.spinDept);
        employee=findViewById(R.id.spinEmp);
        updateby=findViewById(R.id.editText10);
        button=findViewById(R.id.button3);

        Intent i = getIntent();
        slipno.setText(i.getStringExtra("slip_no"));//problem
        updatedate.setText(i.getStringExtra("date"));//problem
        String presentStatus = i.getStringExtra("status").toUpperCase();
        Log.d("stttt", presentStatus+"");


        Spinner spinner = (Spinner)findViewById(R.id.spinStatus);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        for(int j=0;j<10;j++)
        {
            if(nextStatus)
            {
                spinnerAdapter.add(statuses[j]);
                spinnerAdapter.notifyDataSetChanged();
            }
            if(statuses[j].equals(presentStatus))
                nextStatus = true;
        }
//        for(int j=0;j<8;j++)
//        {
//            if(nextStatus)
//            {
//                spinnerAdapter.add(statuses[j]);
//                spinnerAdapter.notifyDataSetChanged();
//            }
//            if(statuses[j].equals(presentStatus))
//                nextStatus = true;
//        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmpty();

            }
        });

    }

    private void checkEmpty() {
        if(TextUtils.isEmpty(id.getText().toString().trim())){
            id.setError("field cannot be empty");
        }

        else if(TextUtils.isEmpty(nextupdatedate.getText().toString().trim())){
            nextupdatedate.setError("field cannot be empty");
        }

       else if(TextUtils.isEmpty(updateby.getText().toString().trim())){
            updateby.setError("field cannot be empty");
        }
       else{
            sendUpdate();
            finish();
        }
    }

    private void sendUpdate() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spin1 = (Spinner) parent;
        Spinner spin2 = (Spinner) parent;

        if (spin1.getId() == R.id.spinStatus) {
            field = parent.getItemAtPosition(position).toString();

        }
        if (spin2.getId() == R.id.spinDept) {
//            limit =  Integer.parseInt(parent.getItemAtPosition(position).toString());
//            Toast.makeText(this, ""+limit, Toast.LENGTH_SHORT).show();
//            offset=0;
//            getTaskJson();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onTaskComplete(String result) {

    }
}
