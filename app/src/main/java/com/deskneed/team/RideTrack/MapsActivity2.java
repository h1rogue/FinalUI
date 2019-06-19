package com.deskneed.team.RideTrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.deskneed.team.ApplicationVariable;
import com.deskneed.team.VvVolleyClass;
import com.deskneed.team.VvVolleyInterface;
import com.deskneed.team.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback, VvVolleyInterface {

    private GoogleMap mMap;
    public static RideDetailClass ride;
    public static List<LatLng> latList;
    long a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        latList = new ArrayList<>();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        loadData();
    }

    private void loadData() {
        Intent intent = getIntent();
        TripData tripData;
        try {
            tripData = (TripData) intent.getSerializableExtra("tripData");

            VvVolleyClass vvVolleyClass = new VvVolleyClass(this, getApplicationContext());
            HashMap params = new HashMap<>();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("trip", tripData.id);
            params.put("phone", ApplicationVariable.ACCOUNT_DATA.contact);
            params.put("token", ApplicationVariable.ACCOUNT_DATA.token);
            params.put("regId", ApplicationVariable.ACCOUNT_DATA.reg_id);
            params.put("filter", jsonObject.toString());
            vvVolleyClass.makeRequest("http://admin.doorhopper.in/api/vdhp/team/ride/track/get", params);

        } catch (Exception e) {
            e.printStackTrace();
        }

//        SharedPreferences preferences = getSharedPreferences("GAME", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = preferences.getString(name, null);
//        //Type type = new TypeToken<ArrayList<RideDetailClass>>(){}.getType();
//        ride = gson.fromJson(json, RideDetailClass.class);
//        Log.d("details", ride.start_id + "," + ride.end_id);
//        a = Long.parseLong(ride.start_id);
//        b = Long.parseLong(ride.end_id);
//        getTrackFromAPI();
//
//        if (ride == null) {
//            ride = new RideDetailClass(null, null, null, 0.0f, 0.0f, 0.0f, null);
//        }
    }

    private void getTrackFromAPI() {


    }

    @Override
    public void onTaskComplete(String result) {
        Log.d("details", result);
        try {
            JSONObject o = new JSONObject(result);
            JSONArray a1 = o.getJSONArray("data_rows");
            for (int i = 1; i < a1.length(); ++i) {//for accessing the json array "data_rows"

                double la1 = Double.parseDouble(a1.getJSONObject(i).getString("latitude"));
                double ln1 = Double.parseDouble(a1.getJSONObject(i).getString("longitude"));
                double la2 = Double.parseDouble(a1.getJSONObject(i - 1).getString("latitude"));
                double ln2 = Double.parseDouble(a1.getJSONObject(i - 1).getString("longitude"));
                LatLng latLng2 = new LatLng(la2, ln2);
                LatLng latLng1 = new LatLng(la1, ln1);
                // Log.i("AAAA", new LatLng(lat, lng) + " " + latList.get(0));
                if (i == 1) {
                    mMap.addMarker(new MarkerOptions().position(latLng2).title("START"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 15));
                    ArrayList<LatLng> points = new ArrayList<LatLng>();
                    PolylineOptions polyLineOptions = new PolylineOptions();
                    points.add(latLng2);
                    points.add(latLng1);
                    polyLineOptions.width(10);
                    polyLineOptions.geodesic(true);
                    polyLineOptions.color(R.color.colorPrimaryDark);
                    polyLineOptions.addAll(points);
                    Polyline polyline = mMap.addPolyline(polyLineOptions);
                    polyline.setGeodesic(true);
                } else if (i == a1.length() - 1) {
                    mMap.addMarker(new MarkerOptions().position(latLng1).title("STOP"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 15));
                    ArrayList<LatLng> points = new ArrayList<LatLng>();
                    PolylineOptions polyLineOptions = new PolylineOptions();
                    points.add(latLng2);
                    points.add(latLng1);
                    polyLineOptions.width(10);
                    polyLineOptions.geodesic(true);
                    polyLineOptions.color(R.color.colorPrimaryDark);
                    polyLineOptions.addAll(points);
                    Polyline polyline = mMap.addPolyline(polyLineOptions);
                    polyline.setGeodesic(true);
                } else {
                    ArrayList<LatLng> points = new ArrayList<LatLng>();
                    PolylineOptions polyLineOptions = new PolylineOptions();
                    points.add(latLng2);
                    points.add(latLng1);
                    polyLineOptions.width(10);
                    polyLineOptions.geodesic(true);
                    polyLineOptions.color(R.color.colorPrimaryDark);
                    polyLineOptions.addAll(points);
                    Polyline polyline = mMap.addPolyline(polyLineOptions);
                    polyline.setGeodesic(true);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
