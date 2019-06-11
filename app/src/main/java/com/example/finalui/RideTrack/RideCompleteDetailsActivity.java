package com.example.finalui.RideTrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.finalui.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RideCompleteDetailsActivity extends AppCompatActivity {
private RideDetailClass ride;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_complete_details);
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");

        SharedPreferences preferences = getSharedPreferences("GAME",MODE_PRIVATE);
        Gson gson  = new Gson();
        String json = preferences.getString(name,null);
        //Type type = new TypeToken<ArrayList<RideDetailClass>>(){}.getType();
        ride=gson.fromJson(json,RideDetailClass.class);
        Log.d("AAA",ride.getTripname());
        Log.d("AAA",ride.getTrack().get(0).toString());
        if(ride==null){
            ride=new RideDetailClass(null,null,0.0f,0.0f,null);
        }
    }
}
