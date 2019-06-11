package com.example.finalui.RideTrack;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.finalui.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.finalui.RideTrack.MapsActivity.lat1;
import static com.example.finalui.RideTrack.MapsActivity.latLngList;
import static com.example.finalui.RideTrack.MapsActivity.lng1;
import static com.example.finalui.RideTrack.MapsActivity.mMap;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static RideDetailClass ride;
    public static List<LatLng> latList;

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
        String name = intent.getStringExtra("NAME");

        SharedPreferences preferences = getSharedPreferences("GAME",MODE_PRIVATE);
        Gson gson  = new Gson();
        String json = preferences.getString(name,null);
        //Type type = new TypeToken<ArrayList<RideDetailClass>>(){}.getType();
        ride=gson.fromJson(json,RideDetailClass.class);
        latList=ride.getTrack();
        for(int i=1;i<latList.size();i++) {
                LatLng latLng2 = latList.get(i-1);
                LatLng latLng1=latList.get(i);
           // Log.i("AAAA", new LatLng(lat, lng) + " " + latList.get(0));
            if(i==1)
            {
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
            }
            else if(i==latList.size()-1)
            {
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
            }
            else{
                ArrayList<LatLng> points = new ArrayList<LatLng>();
                PolylineOptions polyLineOptions = new PolylineOptions();
                points.add(latLng2);
                points.add(latLng1);
                polyLineOptions.width(10);
                polyLineOptions.geodesic(true);
                polyLineOptions.color(R.color.colorPrimaryDark);
                polyLineOptions.addAll(points);
                Polyline polyline = mMap.addPolyline(polyLineOptions);
                polyline.setGeodesic(true);}
        }
        if(ride==null){
            ride=new RideDetailClass(null,null,0.0f,0.0f,null);
        }
    }
}
