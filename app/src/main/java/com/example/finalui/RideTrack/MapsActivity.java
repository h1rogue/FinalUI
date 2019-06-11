package com.example.finalui.RideTrack;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.finalui.BuildConfig;
import com.example.finalui.HomeActivity;
import com.example.finalui.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.example.finalui.HomeActivity.dist;
import static com.example.finalui.HomeActivity.dura;
import static com.example.finalui.HomeActivity.pause1;
import static com.example.finalui.HomeActivity.resume;
import static com.example.finalui.HomeActivity.start1;
import static com.example.finalui.HomeActivity.vv;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    public static MapsActivity fa;
    public static GoogleMap mMap;
    public static double lat1=0,lng1=0;
    public static final String TAG = MapsActivity.class.getSimpleName();
    public static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private MyReceiver myReceiver;
    public static LocationUpdatesService mService = null;
    private boolean mBound = false;
    public static Button mRequestLocationUpdatesButton;
    private double d1,d2;
    boolean isClick;
    private Location mLastLocation;
    SharedPreferences sharedPreferences;

    public static LocationManager manager;
    public static List<LatLng> latLngList;

    LocationRequest mLocationRequest;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;

    public static TextView km, speed1,aa,bb;
    public static Chronometer chronometer;
    public static boolean running;
    public static double kms = 0.00d, finspeed = 0.00d;
    public static long pause, ppp;
    public static float temp = 0.00f;
    public static String jj = "0.00";
    public static String durations = null;
    public static long durationTime, c = 0, i = 0,c1=0;
    public static int findur = 0, findur1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fa=this;

        Log.i("Create", "is working"+String.valueOf(i));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        myReceiver = new MyReceiver();
        latLngList=new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (Utils.requestingLocationUpdates(this)) {
            if (!checkPermissions()) {
                requestPermissions();
            }
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
         manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("lat1", null);
        edit.putString("lng1", null);
        edit.apply();

        mRequestLocationUpdatesButton = findViewById(R.id.button);
        bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);

        chronometer = findViewById(R.id.duration);
        km = findViewById(R.id.km);
        speed1 = findViewById(R.id.speed);
        aa=findViewById(R.id.textView4);
        bb=findViewById(R.id.textView8);
            aa.setVisibility(View.INVISIBLE);
            bb.setVisibility(View.INVISIBLE);
            if(vv==6) {
                km.setText("0.00");
                kms=0;
            }
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); // 10 sec  interval
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                requestPermissions();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mMap.setMyLocationEnabled(true);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if(c!=0)
                return;
            List<Location> locationList = locationResult.getLocations();
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());

                //      Toast.makeText(MapsActivity.this, location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                mLastLocation = location;
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                if(c==0)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }
    };

        @Override
        public void onLocationChanged(Location location) {
            mLastLocation = location;
            //Place current location marker
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

        }

//    @Override
//    public void onBackPressed() {
//        moveTaskToBack(true);
//        super.onBackPressed();
//
//
//    }

//    @Override
//    public void onBackPressed() {//
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivityForResult(intent, 1);
//    }

//    @Override
//    public void onBackPressed() {//
//        startActivity(new Intent(this,HomeActivity.class));
//    }


    @Override
    public void onBackPressed() {
       //     finish();
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }

    @Override
        protected void onStart() {
            super.onStart();

            mRequestLocationUpdatesButton = findViewById(R.id.button);

            mRequestLocationUpdatesButton.setOnClickListener(view -> {
                if (!checkPermissions()) {
                    requestPermissions();
                } else {
                    c++;
                    HomeActivity.cdd=0;

                    aa.setVisibility(View.VISIBLE);
                    bb.setVisibility(View.VISIBLE);
                    Log.i("oo", String.valueOf(c));

                    if (c % 2 == 0 && c != 0) {
                        pause1.setVisibility(View.INVISIBLE);
                        resume.setVisibility(View.VISIBLE);
                        mRequestLocationUpdatesButton.setText("RESUME");

                        chronometer.stop();
                        dura.stop();
                        Log.i("oo", SystemClock.elapsedRealtime() + "   "+ chronometer.getBase());
                        pause = SystemClock.elapsedRealtime() - chronometer.getBase();
                        ppp = SystemClock.elapsedRealtime() - dura.getBase();
                        running = false;
                        durationTime = SystemClock.elapsedRealtime();
                    }
                    if (c % 2 != 0 || c == 1){
                        c1=0;
                        resume.setVisibility(View.INVISIBLE);
                        pause1.setVisibility(View.VISIBLE);
                       // start1.setVisibility(View.INVISIBLE);
                        mRequestLocationUpdatesButton.setText("PAUSE");
                        Log.i("oo", SystemClock.elapsedRealtime() + "   "+ pause);

                        chronometer.setBase(SystemClock.elapsedRealtime() - pause);
                        dura.setBase(SystemClock.elapsedRealtime() - ppp);
                        chronometer.start();
                        dura.start();
                        running = true;
                        }

//                    if (c==1) {
//                        start1.setVisibility(View.INVISIBLE);
//                        chronometer.setBase(SystemClock.elapsedRealtime() - pause);
//                       // dura.setBase(SystemClock.elapsedRealtime() - pause);
//
//                        chronometer.start();
//
//                       // dura.start();
//                        running = true;
//
//                    }

                    if(c==-1) {
                        chronometer.stop();
                        dura.stop();
                        pause = SystemClock.elapsedRealtime() - chronometer.getBase();
                        ppp = SystemClock.elapsedRealtime() - dura.getBase();
                        running = false;
                        durationTime = SystemClock.elapsedRealtime();
                        Toast.makeText(MapsActivity.this, "Your duration is " + chronometer.getText(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    mService.requestLocationUpdates();
                }
            });

            mRequestLocationUpdatesButton.setOnLongClickListener(view -> {
                c = -1;
                Intent intent = new Intent(MapsActivity.this, Details.class);
                intent.putExtra("dist", temp);
                intent.putExtra("duration", chronometer.getText());
                startActivity(intent);
                finish();

                return false;
            });


//         Restore the state of the buttons when the activity (re)launches.
//        setButtonsState(Utils.requestingLocationUpdates(this));
//
//         Bind to the service. If the service is in foreground mode, this signals to the service
//         that since this activity is in the foreground, the service can exit foreground mode.
            bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
                    Context.BIND_AUTO_CREATE);

        }

        @Override
        protected void onResume() {
            super.onResume();

            LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                    new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));
        }

        @Override
        protected void onPause() {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
            super.onPause();
        }

    @Override
    protected void onDestroy() {
        Log.i("Create", "Destroy");
            super.onDestroy();

    }

    @Override
        protected void onStop() {
            if (mBound) {
                // Unbind from the service. This signals to the service that this activity is no longer
                // in the foreground, and the service can respond by promoting itself to a foreground
                // service.
                unbindService(mServiceConnection);
                mBound = false;
            }
            super.onStop();
        }

        /**
         * Returns the current state of the permissions needed.
         */
        public boolean checkPermissions() {
            return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }

        private void requestPermissions() {
            boolean shouldProvideRationale =
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION);

            // Provide an additional rationale to the user. This would happen if the user denied the
            // request previously, but didn't check the "Don't ask again" checkbox.
            if (shouldProvideRationale) {
                Log.i(TAG, "Displaying permission rationale to provide additional context.");
                Snackbar.make(
                        findViewById(R.id.button),
                        R.string.permission_rationale,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Request permission
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_PERMISSIONS_REQUEST_CODE);
                            }
                        })
                        .show();
            } else {
                Log.i(TAG, "Requesting permission");
                // Request permission. It's possible this can be auto answered if device policy
                // sets the permission in a given state or the user denied the permission
                // previously and checked "Never ask again".
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }

        /**
         * Callback received when a permissions request has been completed.
         */
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                               @NonNull int[] grantResults) {
            Log.i(TAG, "onRequestPermissionResult");
            if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
                if (grantResults.length <= 0) {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.
                    mService.requestLocationUpdates();
                } else {
                    // Permission denied.
                    Snackbar.make(
                            findViewById(R.id.button),
                            R.string.permission_denied_explanation,
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.settings, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    isClick = true;
                                    // Build intent that displays the App settings screen.
                                    Intent intent = new Intent();
                                    intent.setAction(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",
                                            BuildConfig.APPLICATION_ID, null);
                                    intent.setData(uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            }
        }

        /**
         * Receiver for broadcasts sent by {@link LocationUpdatesService}.
         */
        private class MyReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
                if (location != null) {
                    Toast.makeText(MapsActivity.this, Utils.getLocationText(location),
                            Toast.LENGTH_SHORT).show();

                    sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("loc", String.valueOf(location.getLatitude()));
                    edit.putString("loc1", String.valueOf(location.getLongitude()));
                    edit.apply();

                }
            }
        }
    }

