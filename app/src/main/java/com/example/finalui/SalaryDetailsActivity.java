package com.example.finalui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SalaryDetailsActivity extends AppCompatActivity implements VvVolleyInterface {
    ExpandabelListAdapter listAdapter;
    ExpandableListView expListView;
    List<HeaderModel> listDataHeader;
    HashMap<HeaderModel, List<SalaryModel>> listDataChild;
    SalaryModel salaryModel;
    HashMap<String, SalaryReport> map;
    ArrayList<String> keys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_details);
        //object for the salary details
        map = new HashMap<>();

        expListView =  findViewById(R.id.expandabels);

        getJson();//get the json data from api
    }

    private void getJson() {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        params.put("sort", "month");
        params.put("sort_order", "desc");
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/salary/get", params);
    }

    @Override
    public void onTaskComplete(String result) {
        Log.d("Aa", result);
        try {
            JSONObject o = new JSONObject(result);
            JSONArray a = o.getJSONArray("data_rows");
            int arrSize = a.length();
            for (int i = 0; i < arrSize; ++i) {//for accessing the json array "data_rows"

                String k = a.getJSONObject(i).getString("month");
                k += '_';
                k += a.getJSONObject(i).getString("type");//this k is the key
                SalaryReport salaryReport;
                float ss = 0;

                salaryModel = new SalaryModel(a.getJSONObject(i).getString("id"),
                        a.getJSONObject(i).getString("date_credited"), a.getJSONObject(i).getString("amount"),
                        k, a.getJSONObject(i).getString("type"));

                if (map.containsKey(k) && map.get(k) != null) {
                    SalaryReport tempSalaryReport = map.get(k);
                    ss = tempSalaryReport.amount + Float.parseFloat(salaryModel.amount);
                    tempSalaryReport.amount = ss;
                    tempSalaryReport.records.add(salaryModel);
                } else {
                    ArrayList records = new ArrayList();
                    records.add(salaryModel);
                    salaryReport = new SalaryReport(a.getJSONObject(i).getString("month"),
                            a.getJSONObject(i).getString("type"), Float.parseFloat(salaryModel.amount), records);

                    map.put(k, salaryReport);
                    keys.add(k);
                }
            }

            listAdapter = new ExpandabelListAdapter(this, map, keys);
            // setting list adapter
            expListView.setAdapter(listAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
