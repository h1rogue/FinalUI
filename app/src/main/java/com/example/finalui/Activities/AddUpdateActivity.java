package com.example.finalui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalui.ApplicationVariable;
import com.example.finalui.Models.UpdateModel;
import com.example.finalui.R;
import com.example.finalui.VvVolleyClass;
import com.example.finalui.VvVolleyInterface;
import com.example.finalui.fragments.itemupdatefragment;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddUpdateActivity extends AppCompatActivity implements VvVolleyInterface, AdapterView.OnItemSelectedListener{

    boolean nextStatus;
    private EditText id,nextupdatedate,updateby;
    private TextView slipno,updatedate;
    private Button button;
    Spinner status,department,employee;
    HashMap<Integer, Pair<String,String>> map ;
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
        map = new HashMap<>();

        Intent i = getIntent();
        slipno.setText(i.getStringExtra("slip_no"));//problem
        updatedate.setText(i.getStringExtra("date"));//problem
        String presentStatus = i.getStringExtra("status").toUpperCase();
        Log.d("stttt", presentStatus+"");
        getDept();

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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmpty();

            }
        });

    }

    private void getDept() {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/department/get", params);

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
            getDeptList();

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
        try {
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getString("responseFor").equals("department/get"))
            {
                JSONArray jsonArray = jsonObject.getJSONArray("data_rows");
                for(int i=0;i<jsonArray.length();i++)
                {
                    Pair<String,String> pair = Pair.create(jsonArray.getJSONObject(i).getString("department"), jsonArray.getJSONObject(i).getString("statuses"));
                    map.put(jsonArray.getJSONObject(i).getInt("id"), pair);
                    Log.d("string", jsonArray.getJSONObject(i).getString("statuses"));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getDeptList() {
        Log.d("map", map.get(1).first);
        for(int i=0;i<map.size();i++)
        {
           String status = map.get(i).second;
           Log.d("map", status);
        }
    }
}
