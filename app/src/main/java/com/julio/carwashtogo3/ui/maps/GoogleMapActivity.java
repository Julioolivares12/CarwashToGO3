package com.julio.carwashtogo3.ui.maps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.julio.carwashtogo3.MainActivity;
import com.julio.carwashtogo3.R;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_REQUEST_PERMISSION = 11;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static String TAG = MainActivity.class.getSimpleName();

    GoogleMap map;
    SupportMapFragment mapFragment;
    Button btnfind,btnseleccionar;
    LatLng mylocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_google_map );
        String apiKey = getString(R.string.map_key);
        btnfind = findViewById(R.id.btnBuscar);
        btnseleccionar = findViewById(R.id.btnselecionarubicacion );
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
        btnfind.setOnClickListener( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                List<Place.Field> fields = Arrays.asList ( Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG );

                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder (
                        AutocompleteActivityMode.FULLSCREEN, fields ).setLocationBias ( RectangularBounds.newInstance (
                        new LatLng ( 13.591238, -90.319956 ),
                        new LatLng ( 14.166245, -87.707606 )
                ) )
                        .build ( GoogleMapActivity.this );
                GoogleMapActivity.this.startActivityForResult ( intent, AUTOCOMPLETE_REQUEST_CODE );
            }
        } );

        btnseleccionar.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (  );

                if(mylocation != null){
                    Geocoder geocoder =new Geocoder ( getApplicationContext () );
                    try {
                        List<Address> matches = geocoder.getFromLocation(mylocation.latitude,mylocation.longitude,1  );
                        Address bestmatch = (!matches.isEmpty () ? matches.get ( 0 ) : null);
                        String direecion = bestmatch.getAddressLine (0);
                        intent.putExtra ( "DIRECCION", direecion);
                    }catch (IOException ex){

                    }
                }
                intent.putExtra ( "LAT", mylocation != null ? mylocation.latitude : 0);
                intent.putExtra ( "LONG", mylocation != null ? mylocation.longitude : 0);
                setResult ( Activity.RESULT_OK,intent );
                finish ();
            }
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                map.clear();
                Place place = Autocomplete.getPlaceFromIntent(data);
                mylocation = place.getLatLng();
                map.addMarker(new MarkerOptions ().position(place.getLatLng()).title(place.getName()));
                map.animateCamera( CameraUpdateFactory.newLatLngZoom(place.getLatLng(),15));
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        UiSettings settings = map.getUiSettings();
        settings.setZoomControlsEnabled(true);
        //Enable current location
        //Here we want to check the permission of location
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //Here we put code if permission is not Granted !!
            // We need to let the user allowing the permission

            //After Making Android 6 (Marshmallow Version)
            //Android make the user to grant the permission during
            // the runtime .... so when the app is running, permission
            // check will appear to user ...

            // Android in this way, increases the security of  Systems
            // Apps, & User Privacy
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},MY_REQUEST_PERMISSION);
            }
            return;
        }else{
            //Here the code of Granted Permission !
            map.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager) getSystemService ( Context.LOCATION_SERVICE );
            Criteria criteria = new Criteria (  );
            Location location = locationManager.getLastKnownLocation ( locationManager.getBestProvider ( criteria,false ) );

            // Add a marker in current Location, and move the camera.
            mylocation = new LatLng(location.getLatitude (), location.getLongitude ());
            map.addMarker(new MarkerOptions().position(mylocation).title("Mi posicion"));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,10));
        }

        // Add a marker in Sydney, Australia, and move the camera.
       // LatLng sv = new LatLng(13.7483454, -89.4907349);
        //map.addMarker(new MarkerOptions().position(sv).title("El Salvador"));
        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(sv,10));
    }
}
