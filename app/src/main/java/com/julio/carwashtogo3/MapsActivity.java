package com.julio.carwashtogo3;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        com.google.android.libraries.places.compat.ui.PlaceAutocompleteFragment autocompleteFragment = (com.google.android.libraries.places.compat.ui.PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener () {
            @Override
            public void onPlaceSelected(com.google.android.libraries.places.compat.Place place) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions ().position(place.getLatLng()).title(place.getName().toString()));
                mMap.moveCamera( CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 12.0f));

            }

            @Override
            public void onError(Status status) {

            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng sydney = new LatLng (13.7006742,-89.2038779);
        mMap.addMarker(new MarkerOptions ().position(sydney).title("CarwashToGo"));
        mMap.moveCamera( CameraUpdateFactory.newLatLng(sydney));



        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
