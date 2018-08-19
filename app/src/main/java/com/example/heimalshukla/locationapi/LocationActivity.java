package com.example.heimalshukla.locationapi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.location.LocationManager.GPS_PROVIDER;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "LocationActivity";
    private TextView latitudeField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;
    private  Location location ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        latitudeField =  findViewById(R.id.TextView02);
        longitudeField =  findViewById(R.id.TextView04);


        if(ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED){
            Log.d(TAG, "onCreate: inside one ");

            //  if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){

            //    Toast.makeText(this, "LOCATION PERMISSION NEEDED", Toast.LENGTH_SHORT).show();
            //   Log.d(TAG, "onCreate: inside 3");
            //  }
            //  else
            {
                Log.d(TAG, "onCreate: inside two ");

                ActivityCompat.requestPermissions(this , new String[] { Manifest.permission.ACCESS_FINE_LOCATION ,
                Manifest.permission.ACCESS_COARSE_LOCATION }, 1);
                Log.d(TAG, "onCreate: inside 3");
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



    }

    public void Locate(View view){

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null ) Log.d(TAG, "onCreate: locationmanager");
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        if (provider==null) Log.d(TAG, "onCreate: provider");

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) ==
                PackageManager.PERMISSION_GRANTED){

        }
        location = locationManager.getLastKnownLocation(provider);


        if(location==null) Log.d(TAG, "onCreate: location ");
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            Log.d("gh", "onCreate: permission "+ location.getLatitude() + location.getLongitude());
            onLocationChanged(location);
        } else {
            latitudeField.setText("Location not available");
            longitudeField.setText("Location not available");
        }

    }
    public void OpenMap (View view){
        if (location != null) {
            Intent i = new Intent(this, MapsActivity.class);
            i.putExtra("latitude", location.getLatitude());
            i.putExtra("longitude", location.getLongitude());
            startActivity(i);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
       // locationManager.removeUpdates(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ContextCompat.checkSelfPermission(this,LOCATION_SERVICE) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(provider,400,1,this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat =  (location.getLatitude());
        double lng =  (location.getLongitude());
        latitudeField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
