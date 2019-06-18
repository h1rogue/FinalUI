package com.example.finalui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalui.Adapters.MyAdapter;
import com.example.finalui.ApplicationVariable;
import com.example.finalui.ExpandabelListAdapter;
import com.example.finalui.Models.CommentModel;
import com.example.finalui.Models.PuchaseModel;
import com.example.finalui.Models.Tasks;
import com.example.finalui.Models.UpdateModel;
import com.example.finalui.R;
import com.example.finalui.SalaryModel;
import com.example.finalui.SalaryReport;
import com.example.finalui.TasksModel;
import com.example.finalui.VvVolleyClass;
import com.example.finalui.VvVolleyInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener, VvVolleyInterface, AdapterView.OnItemSelectedListener {
    RecyclerView recyclerView;
    MyAdapter mAdapter;
    Spinner spinner,spinner2;
    SearchView searchView;
    TextView offsettext;
    Button activebutt,archievebutt,offsetsetbutt,offsetbeforebutt;
    ImageButton reloadbutton;
    public static List<Tasks> tasks;
    List<UpdateModel> updateModelList;
    List<UpdateModel> updateModelListforIndividual;
    String field="slip_no";
    int  limit=10;
    int offset=0,data_row_size;
    Map<String,Pair<String,String>> hasmap;
    List<PuchaseModel> puchaseModelList;
    List<PuchaseModel> purchaseModelListforIndividual;
    List<CommentModel> commentModelList,commentModelListforIndividual;
    List<TasksModel> tasksModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getSupportActionBar().setTitle("Tasks And Assignment");
        hasmap = new HashMap<>();
        Pair<String,String> pair1 = Pair.create("slip_no","type");
        Pair<String,String> pair2 = Pair.create(" priority","type");
        Pair<String,String> pair3 = Pair.create("slip_date","type");
        Pair<String,String> pair4 = Pair.create("customer","type");
        Pair<String,String> pair5 = Pair.create("requirement","type");
        Pair<String,String> pair6 = Pair.create("vendor","type");
        Pair<String,String> pair7 = Pair.create("current_status","type");
        Pair<String,String> pair8 = Pair.create("next_date","type");
        Pair<String,String> pair9 = Pair.create("current_deartment","type");
        Pair<String,String> pair10 = Pair.create("current_employee","type");
        //Added to hashmap
        hasmap.put("Slip no.",pair1);
        hasmap.put("Priority",pair2);
        hasmap.put("Slip date",pair3);
        hasmap.put("Customer",pair4);
        hasmap.put("Requirement",pair5);
        hasmap.put("Vendor",pair6);
        hasmap.put("Current Status",pair7);
        hasmap.put("Next Date",pair8);
        hasmap.put("Current Department",pair9);
        hasmap.put("Current Employee",pair10);
        recyclerView = findViewById(R.id.recy);
        activebutt=findViewById(R.id.button6);
        archievebutt=findViewById(R.id.button8);
        reloadbutton=findViewById(R.id.reloadbutt);
        spinner=findViewById(R.id.spinner);
        spinner2=findViewById(R.id.spinner2);
        offsettext=findViewById(R.id.textView16);
        offsetsetbutt=findViewById(R.id.button11);
        offsetbeforebutt=findViewById(R.id.button12);
        //ArrayAdapter for spinner 1
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this
                ,R.array.drop_down,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        // ArrayAdapter for spinner 2
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this
                ,R.array.drop_down2,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
        searchView=findViewById(R.id.searchView);
        updateModelList=new ArrayList<>();
        updateModelListforIndividual = new ArrayList<>();
        purchaseModelListforIndividual = new ArrayList<>();
        commentModelListforIndividual = new ArrayList<>();
        puchaseModelList=new ArrayList<>();
        commentModelList=new ArrayList<>();
        tasksModels=new ArrayList<>();
        getTaskJson();//takes json data from the task api
        Log.d("damm",data_row_size+"");
        getUpdateadPurchaseComment();
    }
    private void getUpdateadPurchaseComment() {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        params.put("filter", new ArrayList<>().toString());
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/order/slip/update/get", params);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/order/slip/purchase/get", params);
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/order/slip/comment/get", params);
    }

    private void getTaskJson() {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        params.put("filter", new ArrayList<>().toString());
        params.put("sort",field);
        params.put("sort_order","desc");
        params.put("limit",limit+"");
        params.put("offset",offset+"");
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/order/slip/get", params);

    }

    @Override
    public void onTaskComplete(String result) {

        Log.d("task", result);
        try {
            JSONObject o = new JSONObject(result);

            Log.d("damm",data_row_size+"");
            JSONArray a = o.getJSONArray("data_rows");

            if(o.getString("responseFor").equals("order/slip/update/get"))//for updates
            {
                for (int i = 0; i < a.length(); ++i) {//for accessing the json array "data_rows"

                    UpdateModel updateModel = new UpdateModel(a.getJSONObject(i).getString("id"),
                            a.getJSONObject(i).getString("slip_no"), a.getJSONObject(i).getString("date"),
                            a.getJSONObject(i).getString("status"), a.getJSONObject(i).getString("next_date"),
                            a.getJSONObject(i).getString("department"), a.getJSONObject(i).getString("employee"),
                            a.getJSONObject(i).isNull("remarks") ? "N/A" : a.getJSONObject(i).getString("remarks"));
                    updateModelList.add(updateModel);
                }
            }

            if(o.getString("responseFor").equals("order/slip/payment/get"))//for updates
            {
                for (int i = 0; i < a.length(); ++i) {//for accessing the json array "data_rows"

                    PuchaseModel puchaseModel = new PuchaseModel(a.getJSONObject(i).getString("slip_no"),
                            a.getJSONObject(i).getString("date"), a.getJSONObject(i).getString("vendor"),
                            a.getJSONObject(i).getString("invoice"),a.getJSONObject(i).getString("amount"),
                            a.getJSONObject(i).getString("payment"),a.getJSONObject(i).getString("remarks"));
                    puchaseModelList.add(puchaseModel);
                }
            }
        //Comment List

            if(o.getString("responseFor").equals("order/slip/comment/get")){
                for(int i=0;i<a.length();i++){
                    CommentModel commentModel  = new CommentModel(a.getJSONObject(i).getString("staff"),
                            a.getJSONObject(i).getString("comment"),a.getJSONObject(i).getString("commented_on"),
                            a.getJSONObject(i).getString("slip_no"),a.getJSONObject(i).getString("id"));
                    commentModelList.add(commentModel);
                }
            }
            //for tasks
            if(o.getString("responseFor").equals("order/slip/get")) {
                tasks = new ArrayList<>();
                data_row_size = Integer.parseInt(o.getString("data_rows_size"));
                for (int i = 0; i < a.length(); ++i) {//for accessing the json array "data_rows"

                    //Log.d("task",a.getJSONObject(i).isNull("people")?"N/A":a.getJSONObject(i).getString("people"));//to check if element named "people" is there in the data_rows array or not.

                    TasksModel tasksModel1 = new TasksModel(a.getJSONObject(i).getString("slip_no"),
                            a.getJSONObject(i).getString("customer"), a.getJSONObject(i).getString("requirement"),
                            a.getJSONObject(i).getString("vendor"), a.getJSONObject(i).getString("current_department"),
                            a.getJSONObject(i).getString("next_date"), a.getJSONObject(i).getString("current_employee"),
                            a.getJSONObject(i).getString("slip_date"));


                    Tasks tasks1;
                    tasks1 = new Tasks(a.getJSONObject(i).getString("slip_date"),
                            a.getJSONObject(i).getString("slip_no"),
                            a.getJSONObject(i).getString("customer"),
                            a.getJSONObject(i).getString("requirement"),
                            a.getJSONObject(i).isNull("people") ? "N/A" : a.getJSONObject(i).getString("people"),
                            a.getJSONObject(i).isNull("task") ? "N/A" : a.getJSONObject(i).getString("task"),
                            a.getJSONObject(i).isNull("task_assigned") ? "N/A" : a.getJSONObject(i).getString("task_assigned"),
                            a.getJSONObject(i).isNull("action_time") ? "N/A" : a.getJSONObject(i).getString("action_time"),
                            a.getJSONObject(i).isNull("duration") ? "N/A" : a.getJSONObject(i).getString("duration"),
                            a.getJSONObject(i).getString("current_status"),
                            updateModelList, puchaseModelList, commentModelList, tasksModel1);
                    tasks.add(tasks1);

                    offsettext.setText(offset+" to "+(offset+limit)+" of "+data_row_size);
                }

                mAdapter = new MyAdapter(getApplicationContext(), tasks);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                //for the divider
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        ((LinearLayoutManager) layoutManager).getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(TaskActivity.this);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createIndividualCommentLists(List<CommentModel> commentModelList, String slip) {
        commentModelListforIndividual=new ArrayList<>();
        for(CommentModel commentModel:commentModelList){
            if(commentModel.getSlip_no().equals(slip)){
                commentModelListforIndividual.add(commentModel);
            }
        }
    }

    private void createIndividualUpdateLists(List<UpdateModel> updateModelList,String slip) {
        updateModelListforIndividual=new ArrayList<>();
        for(UpdateModel updateModel: updateModelList){
            if(updateModel.getSlipno().equals(slip)){
                updateModelListforIndividual.add(updateModel);
            }
        }
    }
    private void createIndividualPurchaseLists(List<PuchaseModel> puchaseModelList,String slip) {
        purchaseModelListforIndividual =new ArrayList<>();
        for(PuchaseModel puchaseModel: puchaseModelList){
            if(puchaseModel.getSlipno().equals(slip)){
                purchaseModelListforIndividual.add(puchaseModel);
                Log.d("pppp",puchaseModel.getSlipno());
            }
        }
    }

    //Handle on recyclerItem click
    @Override
    public void onUpdateClick(int position) {
        Tasks tasks1 = tasks.get(position);
        String slip = tasks.get(position).getSlip_no();
        Intent intent = new Intent(this, DetailSctivity.class);
        Bundle bundle=new Bundle();
        //func to create individual lists
        createIndividualUpdateLists(tasks1.getUpdateModelList(),slip);

        bundle.putSerializable("ARRAYLIST",(Serializable)updateModelListforIndividual);
        intent.putExtra("BUNDLE",bundle);

        Bundle bundle1=new Bundle();//for sending the whole list to another activity
        //func to create individual purchase list
        createIndividualPurchaseLists(tasks1.getPuchaseModelList(),slip);

        bundle1.putSerializable("ARRAYLIST1",(Serializable)purchaseModelListforIndividual);
        intent.putExtra("BUNDLE1",bundle1);

        Bundle bundle2=new Bundle();
        //func to create individual comment list
        createIndividualCommentLists(tasks1.getCommentModelList(),slip);
        bundle2.putSerializable("ARRAYLIST2",(Serializable)commentModelListforIndividual);
        intent.putExtra("BUNDLE2",bundle2);
        intent.putExtra("INFO",tasks1.getTasksModel());//send each object<Taskmodel> of the list to the next activity
        startActivity(intent);
    }


    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(this,DetailSctivity.class);
        Bundle bundle = new Bundle();
        Log.d("SLIP","onItemPositionClicked");
        Tasks tasks1 = tasks.get(position);
        String slip = tasks.get(position).getSlip_no();
        Log.d("SLIP",slip);
        createIndividualUpdateLists(tasks1.getUpdateModelList(),slip);

        bundle.putSerializable("ARRAYLIST",(Serializable)updateModelListforIndividual);
        intent.putExtra("BUNDLE",bundle);
        intent.putExtra("pos",1);
        intent.putExtra("INFO",tasks1.getTasksModel());
        Bundle bundle1=new Bundle();//for sending the whole list to another activity
        bundle1.putSerializable("ARRAYLIST1",(Serializable)purchaseModelListforIndividual);
        intent.putExtra("BUNDLE1",bundle1);
        Bundle bundle2=new Bundle();
        bundle2.putSerializable("ARRAYLIST2",(Serializable)commentModelListforIndividual);
        intent.putExtra("BUNDLE2",bundle2);
        startActivity(intent);
    }


    //Handle Spinner Selections

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spin1 = (Spinner) parent;
        Spinner spin2 = (Spinner) parent;

        if(spin1.getId()==R.id.spinner){
          String fieldkey = parent.getItemAtPosition(position).toString();
          field = hasmap.get(fieldkey).first;
           getTaskJson();
        }
        if(spin2.getId()==R.id.spinner2){
             limit =  Integer.parseInt(parent.getItemAtPosition(position).toString());
            Toast.makeText(this, ""+limit, Toast.LENGTH_SHORT).show();
             offset=0;
            getTaskJson();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void OffsetBeforeButtPressed(View view) {
        offset-=limit;
        if(offset<0)
            offset=0;
        else
        getTaskJson();
        }
    public void OffsetButtonPressed(View view) {
        offset+=limit;
        getTaskJson();
        offsettext.setText("");
    }

    public void reLoadData(View view) {
        getTaskJson();
    }

    public void activeButtonOnClick(View view) {

       String[] arr = {"initiated","follow","site_visit","quotation","quotation_follow"
       ,"work_due","work_progress","invoice","payment","feedback"};
       JsonArray jsonArray = new JsonArray();
       jsonArray.add("initiated");
        jsonArray.add("follow");
        jsonArray.add("site_visit");
        jsonArray.add("quotation");
        jsonArray.add("quotation_follow");
        jsonArray.add("work_due");
        jsonArray.add("work_progress");
        jsonArray.add("invoice");
        jsonArray.add("payment");
        jsonArray.add("feedback");
        afterbuttonClicked(jsonArray);
    }

    public void archieveButtonOnClick(View view) {

        JsonArray arr = new JsonArray();
        arr.add("cancelled");
        arr.add("completed");
        afterbuttonClicked(arr);
    }

    private void afterbuttonClicked(JsonArray arr) {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("current_status",arr);
        params.put("filter", jsonObject.toString());
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/order/slip/get", params);

//        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/order/slip/update/get", params);
//        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/order/slip/purchase/get", params);
//        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/order/slip/comment/get", params);

    }

}