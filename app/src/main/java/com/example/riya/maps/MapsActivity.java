package com.example.riya.maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DialogCreate.DialogCreateListener {

    public GoogleMap mMap;

    //EditText name;
    Marker myMarker;       //Draggable marker
    Marker autoMarker;     //user's location marker
    Marker addedMarker;    //added marker to map
    LatLng userLocation;
    LocationManager locationManager;
    LocationListener locationListener;
    String markersetname;
    String CategoryName;

   public void btnpress (View view)  //on pressing the button this method is executed
    {
        openDialog();
    }

    public void markerAdd(){
        Toast.makeText(getApplicationContext(), markersetname, Toast.LENGTH_SHORT).show();
        //shows cordinates of location where marker is placed
        addedMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(myMarker.getPosition().latitude,myMarker.getPosition().longitude)).title(markersetname));
        addedMarker.setTag(CategoryName);
        //adds a marker to that location
        myMarker.setPosition(new LatLng(myMarker.getPosition().latitude,myMarker.getPosition().longitude+0.0001));

    }
    public void openDialog(){
       DialogCreate dialogCreate = new DialogCreate();
       dialogCreate.show(getSupportFragmentManager(),"dialog Create");

    }

    @Override
    public void applyTexts(String marname,String Category) {
      markersetname = marname;
      CategoryName = Category;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) { //checking for the request code of requested permission.

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {//if permission is granted.

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    {//again verify permission. although permission are checked in above line but we can't request for location updates without verifying.

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        //asking for location updates every second
                    }

                }

            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       // name = findViewById(R.id.name); // declaring editText field.

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); //initializing location manager.

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(MapsActivity.this, location.toString(), Toast.LENGTH_SHORT).show();
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                autoMarker.remove();
                autoMarker=mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(220)));
                //
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));


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
        };

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if(lastKnownLocation!=null){userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());}
                else{userLocation = new LatLng(27.9881199,86.9162203);}

                autoMarker=mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(220)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));

                myMarker= mMap.addMarker(new MarkerOptions().position(userLocation).title("Movable Marker").draggable(true).icon(BitmapDescriptorFactory.defaultMarker(25)));
                myMarker.showInfoWindow();
                mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

                    @Override
                    public void onMarkerDragStart(Marker marker) {

                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        Toast.makeText(MapsActivity.this, String.valueOf(myMarker.getPosition().latitude) +" ; "+String.valueOf(myMarker.getPosition().longitude) , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {

                    }
                });


            }
            }
}
