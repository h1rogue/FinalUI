package com.deskneed.team.Activities;

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

import androidx.appcompat.app.AppCompatActivity;

import com.deskneed.team.ApplicationVariable;
import com.deskneed.team.Models.DeptModel;
import com.deskneed.team.Models.EmpModel;
import com.deskneed.team.VvVolleyClass;
import com.deskneed.team.VvVolleyInterface;
import com.deskneed.team.R;

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
    ArrayList<DeptModel> deptArray;
    ArrayList<EmpModel> empModelsArray;

    HashMap<String,Integer> deptIDmap;
    String field,field1;
    String[] statuses = new String[]{"INITIATED","FOLLOW","SITE_VISIT","QUOTATION","QUOTATION_FOLLOW",
                        "WORK_DUE","WORK_PROGRESS","INVOICE","PAYMENT","FEEDBACK"};
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
        deptArray = new ArrayList<>();
        empModelsArray = new ArrayList<>();
        deptIDmap = new HashMap<>();
        getDeptandEmployee();
        Intent i = getIntent();
        slipno.setText(i.getStringExtra("slip_no"));//problem
        updatedate.setText(i.getStringExtra("date"));//problem
        String presentStatus = i.getStringExtra("status").toUpperCase();

        Spinner spinner = (Spinner)findViewById(R.id.spinStatus);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        int k=0;
        for(int j=0;j<10;j++)
        {
            if(nextStatus)
            {
                if(k==0)
                    spinnerAdapter.add(statuses[j-1]);
                k++;
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

    private void getDeptandEmployee() {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/department/get", params);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/staff/get", params);
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
        Log.d("map", field+"uuy");

        if (spin1.getId() == R.id.spinStatus) {
            field = parent.getItemAtPosition(position).toString();
             Log.d("click", field+"");
            Log.d("map", field+"uuy");
            getDeptList();

        }
        if (spin1.getId() == R.id.spinDept) {
            field1 = parent.getItemAtPosition(position).toString();
            Log.d("map", field1+"uuy");
            getEmpList();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onTaskComplete(String result) {
        try {

            Log.d("DSK_OPER",result);
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getString("responseFor").equals("department/get"))
            {
                JSONArray jsonArray = jsonObject.getJSONArray("data_rows");
                for(int i=0;i<jsonArray.length();i++)
                {
                    String str = jsonArray.getJSONObject(i).getString("statuses");
                    String[] arr = str.split(",");//to seperate the comma separated items into string array
                    ArrayList<String> deptarraylist = new ArrayList<>();
                    for(String s: arr)
                    {
                        deptarraylist.add(s);
                    }

                    jsonArray.getJSONObject(i).optJSONArray("statuses");
                    DeptModel deptModel = new DeptModel(jsonArray.getJSONObject(i).getInt("id"),
                            jsonArray.getJSONObject(i).getString("department"),
                            deptarraylist);
                    deptIDmap.put(jsonArray.getJSONObject(i).getString("department"),
                            jsonArray.getJSONObject(i).getInt("id"));
                     deptArray.add(deptModel);
                }

            }
            if(jsonObject.getString("responseFor").equals("staff/get"))
            {
                JSONArray jsonArray = jsonObject.getJSONArray("data_rows");
                Log.d("mapp1", jsonArray.length()+"") ;
                for(int j=0;j<jsonArray.length();j++)
                {
                    Log.d("mapp",jsonArray.getJSONObject(j).getString("department"));
                    if(jsonArray.getJSONObject(j).getString("department").equals("null"))
                    {
                        Log.d("mapp",jsonArray.getJSONObject(j).getString("department"));

                        EmpModel empModel = new EmpModel(0,
                                jsonArray.getJSONObject(j).getString("name"));
                        Log.d("mapp", jsonArray.getJSONObject(j).getString("name")+"") ;
                        empModelsArray.add(empModel);
                    }
                    else{
                        EmpModel empModel = new EmpModel(jsonArray.getJSONObject(j).getInt("department"),
                                jsonArray.getJSONObject(j).getString("name"));
                        Log.d("mapp", jsonArray.getJSONObject(j).getString("name")+"") ;
                        empModelsArray.add(empModel);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getDeptList() {

        Spinner spinner1 = (Spinner)findViewById(R.id.spinDept);
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(spinnerAdapter1);
        spinner1.setOnItemSelectedListener(this);

        for(DeptModel deptModel:deptArray){
            if(deptModel.getStatuses().contains(field.toLowerCase())){
                Log.d("click",deptModel.getStatuses().get(0)+"");
                spinnerAdapter1.add(deptModel.getDept());
                spinnerAdapter1.notifyDataSetChanged();
            }
        }
    }
    private void getEmpList() {
        Spinner spinner2 = (Spinner)findViewById(R.id.spinEmp);
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(spinnerAdapter2);
        spinner2.setOnItemSelectedListener(this);

        for(EmpModel empModel:empModelsArray){

            if(empModel.getDept()==(deptIDmap.get(field1))||empModel.getDept()==deptIDmap.get("ADMINISTRATION")){
                spinnerAdapter2.add(empModel.getName());
                spinnerAdapter2.notifyDataSetChanged();
            }
        }
    }

}
