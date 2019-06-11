package com.example.finalui.RideTrack;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.finalui.HomeActivity;
import com.example.finalui.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static com.example.finalui.HomeActivity.cdd;
import static com.example.finalui.HomeActivity.dist;
import static com.example.finalui.HomeActivity.dura;
import static com.example.finalui.HomeActivity.openOrClose;
import static com.example.finalui.RideTrack.MapsActivity.c;
import static com.example.finalui.RideTrack.MapsActivity.c1;
import static com.example.finalui.RideTrack.MapsActivity.chronometer;
import static com.example.finalui.RideTrack.MapsActivity.durations;
import static com.example.finalui.RideTrack.MapsActivity.findur;
import static com.example.finalui.RideTrack.MapsActivity.findur1;
import static com.example.finalui.RideTrack.MapsActivity.i;
import static com.example.finalui.RideTrack.MapsActivity.jj;
import static com.example.finalui.RideTrack.MapsActivity.km;
import static com.example.finalui.RideTrack.MapsActivity.kms;
import static com.example.finalui.RideTrack.MapsActivity.lat1;
import static com.example.finalui.RideTrack.MapsActivity.latLngList;
import static com.example.finalui.RideTrack.MapsActivity.lng1;
import static com.example.finalui.RideTrack.MapsActivity.mMap;
import static com.example.finalui.RideTrack.MapsActivity.speed1;
import static com.example.finalui.RideTrack.MapsActivity.temp;
import static java.lang.StrictMath.abs;

/**
 * A bound and started service that is promoted to a foreground service when location updates have
 * been requested and all clients unbind.
 *
 * For apps running in the background on "O" devices, location is computed only once every 10
 * minutes and delivered batched every 30 minutes. This restriction applies even to apps
 * targeting "N" or lower which are run on "O" devices.
 *
 * This sample show how to use a long-running service for location updates. When an activity is
 * bound to this service, frequent location updates are permitted. When the activity is removed
 * from the foreground, the service promotes itself to a foreground service, and location updates
 * continue. When the activity comes back to the foreground, the foreground service stops, and the
 * notification assocaited with that service is removed.
 */
public class LocationUpdatesService extends Service {

    private static final String PACKAGE_NAME =
            "com.google.android.gms.location.sample.locationupdatesforegroundservice";

    private static final String TAG = LocationUpdatesService.class.getSimpleName();

    /**
     * The name of the channel for notifications.
     */
    private static final String CHANNEL_ID = "channel_01";
    SharedPreferences prefs;
    static final String ACTION_BROADCAST = PACKAGE_NAME + ".broadcast";

    static final String EXTRA_LOCATION = PACKAGE_NAME + ".location";
    private static final String EXTRA_STARTED_FROM_NOTIFICATION = PACKAGE_NAME +
            ".started_from_notification";

    private final IBinder mBinder = new LocalBinder();

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS /2;

    /**
     * The identifier for the notification displayed for the foreground service.
     */
    private static final int NOTIFICATION_ID = 12345678;

    private List<Double> ll;
    private double pp=0.001;

    /**
     * Used to check whether the bound activity has really gone away and not unbound as part of an
     * orientation change. We create a foreground service notification only if the former takes
     * place.
     */
    private boolean mChangingConfiguration = false;

    private NotificationManager mNotificationManager;

    /**
     * Contains parameters used by {@link com.google.android.gms.location.FusedLocationProviderApi}.
     */
    private LocationRequest mLocationRequest;

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Callback for changes in location.
     */
    private LocationCallback mLocationCallback;

    private Handler mServiceHandler;

    /**
     * The current location.
     */
    private Location mLocation;


    private String gg = "0";
    private String ggg = "0";

    public LocationUpdatesService() {
    }

    @Override
    public void onCreate() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onNewLocation(locationResult.getLastLocation());
            }
        };

        if (c%2!=0||c==0||c==1) {

            createLocationRequest();
            getLastLocation();
        }
        prefs = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("lat", "0");
        edit.putString("lng", "0");
        edit.apply();

        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mServiceHandler = new Handler(handlerThread.getLooper());
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service started"+i);
        boolean startedFromNotification = intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION,
                false);

        // We got here because the user decided to remove location updates from the notification.
        if (startedFromNotification) {
            removeLocationUpdates();
            stopSelf();
        }
        // Tells the system to not try to recreate the service after it has been killed.
        return START_NOT_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChangingConfiguration = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Called when a client (MainActivity in case of this sample) comes to the foreground
        // and binds with this service. The service should cease to be a foreground service
        // when that happens.
        Log.i(TAG, "dura: " + chronometer.getText());
        dura.setText(chronometer.getText());
        Log.i(TAG, "in onBind()");
        stopForeground(true);
        mChangingConfiguration = false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        // Called when a client (MainActivity in case of this sample) returns to the foreground
        // and binds once again with this service. The service should cease to be a foreground
        // service when that happens.
        Log.i(TAG, "dura: " + chronometer.getText());
      //  dura.setText(chronometer.getText());
        dura.setBase(chronometer.getBase());
        dura.start();
        Log.i(TAG, "in onRebind()"+i);
        stopForeground(true);
        mChangingConfiguration = false;
        Log.i("openorclose", i+ " "+ openOrClose);
        if(i==0&&openOrClose){
            Intent intent1=new Intent(this, HomeActivity.class);
            startActivity(intent1);

        i++;}
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "Last client unbound from service");
        Log.i(TAG, "dura: " + chronometer.getText());
        dura.setText(chronometer.getText());
        // Called when the last client (MainActivity in case of this sample) unbinds from this
        // service. If this method is called due to a configuration change in MainActivity, we
        // do nothing. Otherwise, we make this service a foreground service.
        if (!mChangingConfiguration && Utils.requestingLocationUpdates(this)) {
            i--;
            Log.i(TAG, "Unbind Starting foreground service"+ i);
            startForeground(NOTIFICATION_ID, getNotification());
        }
        return true; // Ensures onRebind() is called when a client re-binds.
    }
    @Override
    public void onDestroy() {
        mServiceHandler.removeCallbacksAndMessages(null);
    }

    public void requestLocationUpdates() {
        Log.i("c1", String.valueOf(c1));
        if((c%2==0&&c!=1)||c1==1||cdd==1)//for pause/resume. when paused we dont update the location
        {
            prefs = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("lat1", String.valueOf(0));
            edit.putString("lng1", String.valueOf(0));
            edit.apply();
            return;
        }
        Log.i(TAG, "Requesting location updates");
        Utils.setRequestingLocationUpdates(this, true);
        startService(new Intent(getApplicationContext(), LocationUpdatesService.class));
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.myLooper());
        } catch (SecurityException unlikely) {
            Utils.setRequestingLocationUpdates(this, false);
            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
    }
    public void removeLocationUpdates() {
        Log.i(TAG, "Removing location updates");
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            Utils.setRequestingLocationUpdates(this, false);
            stopSelf();
        } catch (SecurityException unlikely) {
            Utils.setRequestingLocationUpdates(this, true);
            Log.e(TAG, "Lost location permission. Could not remove updates. " + unlikely);
        }
    }

    /**
     * Returns the {@link NotificationCompat} used as part of the foreground service.
     */
    private Notification getNotification() {
        Intent intent = new Intent(this, LocationUpdatesService.class);

        CharSequence text = Utils.getLocationText(mLocation);

        // Extra to help us figure out if we arrived in onStartCommand via the notification or not.
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);

        // The PendingIntent that leads to a call to onStartCommand() in this service.
        PendingIntent servicePendingIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // The PendingIntent to launch activity.
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MapsActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .addAction(R.drawable.common_google_signin_btn_icon_dark, getString(R.string.common_google_play_services_enable_button),
                        servicePendingIntent)
                .setContentText(text)
                .setContentTitle(Utils.getLocationTitle(this))
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_LOW)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(text)
                .setWhen(System.currentTimeMillis());

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }

        return builder.build();
    }

    private void getLastLocation() {
        try {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLocation = task.getResult();
                            } else {
                                Log.w(TAG, "Failed to get locatio n.");
                            }
                         }
                    });
        } catch (SecurityException unlikely) {
            Log.e(TAG, "Lost location permission." + unlikely);
        }
    }
    private void onNewLocation(Location location) {
        if(c%2==0&&c!=1||c1==1) {
            prefs = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("lat1", String.valueOf(0));
            edit.putString("lng1", String.valueOf(0));
            edit.apply();
            return;
        }

        Log.i(TAG, "New location: " + location);
        if(c==-1){
            //starting =
            removeLocationUpdates();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mLocation = location;
        double lat = 0, lng = 0;
        lat=location.getLatitude();
        lng=location.getLongitude();
        gg = prefs.getString("lat1", null);
        ggg = prefs.getString("lng1", null);
        if (gg != null)
            lat1 = Double.parseDouble(gg);
        if (ggg != null)
            lng1 = Double.parseDouble(ggg);
        if (lat1 != 0 && GetDistanceFromLatLonInKm(lat1, lng1, lat, lng) > 0.001 &&MapsActivity.manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            Log.i("Each distance", String.valueOf(GetDistanceFromLatLonInKm(lat1, lng1, lat, lng)));
            kms += GetDistanceFromLatLonInKm(lat1, lng1, lat, lng);
            //    Toast.makeText(MapsActivity.this, "Your speed: " +'\n' + GetDistanceFromLatLonInKm(lat1, lng1, lat, lng)*3600+  '\n' +GetDistanceFromLatLonInKm(lat1, lng1, lat, lng), Toast.LENGTH_LONG).show();

            durations = (String) chronometer.getText();
            char dd3 = durations.charAt(durations.length() - 4);
            char d1 = durations.charAt(durations.length() - 2);
            char d2 = durations.charAt(durations.length() - 1);
            int d3 = (int) d1 - 48;
            int d4 = (int) d2 - 48;
            int dd33 = (int) dd3 - 48;
            findur = dd33 * 60 + d3 * 10 + d4;
            Log.i("lob", String.valueOf(findur));

            double kms1 = abs(temp - kms);
            int kmms1 = abs(findur1 - findur);
            Log.i("kms = ", String.valueOf(kms1));
            Log.i("temp = ", String.valueOf(temp));
            Log.i("dur = ", String.valueOf(findur1));

            double ff = (kms1 * 3600) / kmms1;
            double kk = ff;
            kk = (double) Math.round(kk * 100d) / 100d;
            speed1.setText(kk + " kmph");

            ArrayList<LatLng> points = new ArrayList<LatLng>();
            PolylineOptions polyLineOptions = new PolylineOptions();
            Log.i("poly1", lat1+" "+lng1+" "+ location.getLatitude()+" "+ location.getLongitude());
            points.add(new LatLng(lat1, lng1));
            points.add(new LatLng(location.getLatitude(), location.getLongitude()));
            //changes
            latLngList.add(new LatLng(lat1,lng1));
            latLngList.add(new LatLng(location.getLatitude(),location.getLongitude()));
            polyLineOptions.width(10);
            polyLineOptions.geodesic(true);
            polyLineOptions.color(R.color.colorPrimaryDark);
            polyLineOptions.addAll(points);
            Polyline polyline = mMap.addPolyline(polyLineOptions);
            polyline.setGeodesic(true);

            prefs = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            Log.i("save", location.getLatitude() + "  "+location.getLongitude());
                edit.putString("lat1", String.valueOf(location.getLatitude()));
                edit.putString("lng1", String.valueOf(location.getLongitude()));
            edit.apply();
           // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16 ));
        } else {
            speed1.setText(jj + " kmph");
        }
        kms = (double) Math.round(kms * 100000d) / 100000d;
        km.setText(String.valueOf(kms));
        dist.setText(String.valueOf(kms));

        //i = (long) kk;

       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15 ));//dont move camera when still
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        mMap.setMyLocationEnabled(true);
        prefs = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        Log.i("save", location.getLatitude() + "  "+location.getLongitude());
        if(lng1==0) {
            edit.putString("lat1", String.valueOf(location.getLatitude()));
            edit.putString("lng1", String.valueOf(location.getLongitude()));
        }edit.putFloat("kmm", (float) kms);
        edit.putInt("kmm1", (int) findur);
        edit.apply();
        temp=  prefs.getFloat("kmm", 0);
        findur1 = prefs.getInt("kmm1", 0);

        // Notify anyone listening for broadcasts about the new location.
        Intent intent = new Intent(ACTION_BROADCAST);
        intent.putExtra(EXTRA_LOCATION, location);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        // Update notification content if running as a foreground service.
        if (serviceIsRunningInForeground(this)) {
            mNotificationManager.notify(NOTIFICATION_ID, getNotification());
        }
    }
    /**
     * Sets the location request parameters.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        LocationUpdatesService getService() {
            return LocationUpdatesService.this;
        }
    }

    /**
     * Returns true if this is a foreground service.
     *
     * @param context The {@link Context}.
     */
    public boolean serviceIsRunningInForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                Integer.MAX_VALUE)) {
            if (getClass().getName().equals(service.service.getClassName())) {
                if (service.foreground) {
                    return true;
                }
            }
        }
        return false;
    }

    public double GetDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2)
    {
        final int R = 6371;
        // Radius of the earth in km
        double dLat = deg2rad(lat2 - lat1);
        // deg2rad below
        double dLon = deg2rad(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        // Distance in km
        return d;
    }
    private double deg2rad(double deg)
    {
        return deg * (Math.PI / 180);
    }
}