package com.example.finalui.RideTrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalui.HomeActivity;
import com.example.finalui.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.example.finalui.HomeActivity.dura;
import static com.example.finalui.HomeActivity.fa1;
import static com.example.finalui.RideTrack.MapsActivity.chronometer;
import static com.example.finalui.RideTrack.MapsActivity.latLngList;

public class Details extends AppCompatActivity {
    TextView distance1;
    TextView duration1;
    Button button;
    EditText name;
    String dura;
    float dis;
    List<TripData> tripData;
    NotesAdapter mAdapter;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        distance1 = findViewById(R.id.finaldist);
        duration1 = findViewById(R.id.finaldur);
        button = findViewById(R.id.save);
        name = findViewById(R.id.tripName);
        tripData = new ArrayList<>();
        db = new DatabaseHelper(this);

        Intent i = getIntent();
        dura = i.getStringExtra("duration");
        dis = i.getFloatExtra("dist", 0.00f);
        distance1.setText(String.valueOf(dis));
        duration1.setText(dura);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    public void save() {
        if (name.getText().toString().equals(""))
            Toast.makeText(this, "Please give a name of the Trip!", Toast.LENGTH_SHORT).show();
        else {
            db.insertTripData(name.getText().toString().trim(), dis, dura);
            RideDetailClass rideDetailClass=new RideDetailClass(name.getText().toString().trim(),
                    dura,23.4f,dis,latLngList);

            for(LatLng latLng : latLngList)
                Log.d("AAA", latLng.latitude+","+latLng.longitude);

            createSharedref(rideDetailClass);

//            Intent i = new Intent(Details.this, Trips.class);
//            startActivity(i);
            Toast.makeText(this, "Trip saved!", Toast.LENGTH_LONG).show();
            finish();
            Intent i = new Intent(Details.this, HomeActivity.class);
            fa1.finish();//change
            startActivity(i);
        }
    }
    private void createSharedref(RideDetailClass riderlist) {
        SharedPreferences sharedPreferences = getSharedPreferences("GAME",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(riderlist);
        editor.putString(name.getText().toString().trim(),json);
        editor.apply();
    }
}


