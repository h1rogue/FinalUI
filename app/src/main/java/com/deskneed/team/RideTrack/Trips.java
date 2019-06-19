package com.deskneed.team.RideTrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deskneed.team.ApplicationVariable;
import com.deskneed.team.HomeActivity;
import com.deskneed.team.VvVolleyClass;
import com.deskneed.team.VvVolleyInterface;
import com.deskneed.team.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trips extends Activity implements NotesAdapter.OnItemClickListener, VvVolleyInterface {

    RecyclerView recyclerView;
    NotesAdapter mAdapter;
    List<TripData> tripDataList;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        recyclerView = findViewById(R.id.recy);
        tripDataList = new ArrayList<>();


        getTripFromAPI();//getting the trips from server

    }

    private void getTripFromAPI() {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        params.put("filter", new JSONObject().toString());
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/trip/get", params);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onUpdateClick(int position) {
        Intent intent = new Intent(Trips.this, MapsActivity2.class);
        intent.putExtra("tripData", tripDataList.get(position));
        startActivity(intent);
        Log.d("DHU", tripDataList.get(position).toString());
    }

    @Override
    public void onTaskComplete(String result) {
        Log.d("trips", result);

        try {
            JSONObject o = new JSONObject(result);
            JSONArray a = o.getJSONArray("data_rows");
            for (int i = 0; i < a.length(); ++i) {//for accessing the json array "data_rows"

                    //Log.d("task",a.getJSONObject(i).isNull("people")?"N/A":a.getJSONObject(i).getString("people"));//to check if element named "people" is there in the data_rows array or not.

                    TripData tripData = new TripData(a.getJSONObject(i).getString("id"), a.getJSONObject(i).getString("trip_name"),
                            Float.parseFloat(a.getJSONObject(i).getString("distance")), a.getJSONObject(i).getString("duration"));
                    tripDataList.add(tripData);
            }
            mAdapter = new NotesAdapter(getApplicationContext(), tripDataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            //for the divider
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    ((LinearLayoutManager) layoutManager).getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(Trips.this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
