package com.example.finalui.RideTrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalui.Activities.TaskActivity;
import com.example.finalui.HomeActivity;
import com.example.finalui.R;

import java.util.ArrayList;
import java.util.List;

public class Trips extends Activity implements NotesAdapter.OnItemClickListener{

    RecyclerView recyclerView;
    NotesAdapter mAdapter;
    List<TripData> tripData;
    DatabaseHelper db;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        recyclerView = findViewById(R.id.recy);
        tripData=new ArrayList<>();
       // button=findViewById(R.id.startend);

        db = new DatabaseHelper(Trips.this);

        tripData=db.getTripData();
        mAdapter = new NotesAdapter(getApplicationContext(), tripData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //for the divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(Trips.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

    @Override
    public void onItemClick(int position) {
        //set korisu
    }

    @Override
    public void onUpdateClick(int position) {
        Intent intent = new Intent(Trips.this,MapsActivity2.class);
        TripData tripobj = tripData.get(position);
        String name = tripobj.getName().trim();
        Log.d("AAA",name);
        intent.putExtra("NAME",name);
        startActivity(intent);
    }
}
