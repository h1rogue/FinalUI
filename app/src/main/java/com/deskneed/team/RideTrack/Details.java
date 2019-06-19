package com.deskneed.team.RideTrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.deskneed.team.ApplicationVariable;
import com.deskneed.team.HomeActivity;
import com.deskneed.team.VvVolleyClass;
import com.deskneed.team.VvVolleyInterface;
import com.deskneed.team.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.deskneed.team.HomeActivity.fa2;
import static com.deskneed.team.RideTrack.MapsActivity.latLngList;

public class Details extends AppCompatActivity implements VvVolleyInterface {
    TextView distance1,description;
    TextView duration1;
    Button button;
    EditText name;
    String dura,start_id,end_id,start_time,end_time,trip_name,trip_description;
    float dis,duration;
    List<TripData> tripData;
    NotesAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        distance1 = findViewById(R.id.finaldist);
        duration1 = findViewById(R.id.finaldur);
        button = findViewById(R.id.save);
        description = findViewById(R.id.description);
        name = findViewById(R.id.tripName);
        tripData = new ArrayList<>();
        Intent i = getIntent();
        dura = i.getStringExtra("duration");
        dis = i.getFloatExtra("dist", 0.00f);
        start_id = i.getStringExtra("start_id");
        end_id = i.getStringExtra("end_id");
        start_time = i.getStringExtra("start_time");
        end_time = i.getStringExtra("end_time");
        distance1.setText(String.valueOf(dis));
        duration1.setText(dura);

        //FOR duration in seconds
        int length = dura.length();
        if(length<=5)
            duration = dura.charAt(dura.length()-1)-48 + (dura.charAt(dura.length()-2)-48)*10 + 60 * ((dura.charAt(dura.length()-4)-48) + (dura.charAt(dura.length()-5)-48)*10);
        else
            duration = dura.charAt(dura.length()-1)-48 + (dura.charAt(dura.length()-2)-48)*10 + 60 * (dura.charAt(dura.length()-4) + (dura.charAt(dura.length()-5)-48)*10) + 3600 * ((dura.charAt(dura.length()-7)-48) + (dura.charAt(dura.length()-8)-48)*10) ;

        Log.d("duration", String.valueOf(duration) + "," + dura.length());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    public void save() {
        if (name.getText().toString().equals("")||description.getText().toString().equals(""))
            Toast.makeText(this, "Please give a name & description!", Toast.LENGTH_SHORT).show();
        else {
            trip_name = name.getText().toString();
            trip_description = description.getText().toString();

            RideDetailClass rideDetailClass=new RideDetailClass(start_id,end_id,name.getText().toString().trim(),
                    duration,23.4f,dis,latLngList);

            Log.d("lop", String.valueOf(rideDetailClass.duration));
            for(LatLng latLng : latLngList)
                Log.d("AAA", latLng.latitude+","+latLng.longitude);

            createSharedref(rideDetailClass);

            sendDataToAPI(trip_name,trip_description,duration,dis,start_id,end_id,start_time,end_time);

            Toast.makeText(this, "Trip saved!", Toast.LENGTH_LONG).show();

            Intent i = new Intent(Details.this, HomeActivity.class);
            fa2.finish();
            startActivity(i);
//            activity = HomeActivity.this;
//            restartActivity(activity);
        }
    }

    private void sendDataToAPI(String trip_name, String trip_description, float dura, float dis, String start_id, String end_id, String start_time, String end_time) {
        VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
        HashMap params = new HashMap<>();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("trip_name", trip_name);
        jsonObject.addProperty("description", trip_description);
        jsonObject.addProperty("duration", duration);
        jsonObject.addProperty("distance", dis);
        jsonObject.addProperty("start_id", start_id);
        jsonObject.addProperty("end_id", end_id);
        jsonObject.addProperty("start_time", start_time);
        jsonObject.addProperty("end_time", end_time);

        params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
        params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
        params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
        params.put("filter", new ArrayList<>().toString());
        params.put("new_data_row", jsonObject.toString());//sends the new_data_row array to the api
        vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/trip/create", params);
    }

    private void createSharedref(RideDetailClass riderlist) {
        SharedPreferences sharedPreferences = getSharedPreferences("GAME",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(riderlist);
        editor.putString(name.getText().toString().trim(),json);
        editor.apply();
    }

    @Override
    public void onTaskComplete(String result) {
        Log.d("details1", result);

    }
}


