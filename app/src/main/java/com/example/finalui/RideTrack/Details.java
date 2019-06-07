package com.example.finalui.RideTrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalui.HomeActivity;
import com.example.finalui.R;

import java.util.ArrayList;
import java.util.List;

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
            db.insertTripData(name.getText().toString(), dis, dura);
//            Intent i = new Intent(Details.this, Trips.class);
//            startActivity(i);
            Toast.makeText(this, "Trip saved!", Toast.LENGTH_LONG).show();
            finish();
            Intent i = new Intent(Details.this, HomeActivity.class);
            startActivity(i);
        }
    }
}


