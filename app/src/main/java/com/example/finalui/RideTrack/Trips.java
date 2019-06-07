package com.example.finalui.RideTrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalui.HomeActivity;
import com.example.finalui.R;

import java.util.ArrayList;
import java.util.List;

public class Trips extends Activity {

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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }
}
