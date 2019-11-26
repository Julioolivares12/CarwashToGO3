package com.julio.carwashtogo3.ui.maps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.julio.carwashtogo3.MainActivity;
import com.julio.carwashtogo3.R;
import com.julio.carwashtogo3.model.DirectionsParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapRouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_REQUEST_PERMISSION = 11;
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static String TAG = MainActivity.class.getSimpleName();

    GoogleMap map;
    SupportMapFragment mapFragment;
    Button btnfind,btnseleccionar;
    LatLng mylocation;
    LatLng destino;
    private Polyline polyline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_map_route );
        String apiKey = getString(R.string.map_key);
        btnfind = findViewById(R.id.btnBuscar);
        Bundle data = getIntent ().getExtras ();
        destino = new LatLng ( data.getDouble ( "LAT" ),data.getDouble ( "LONG" ) );
        btnseleccionar = findViewById(R.id.btnselecionarubicacion );
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map_route);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        UiSettings settings = map.getUiSettings();
        settings.setCompassEnabled ( true );
        settings.setZoomControlsEnabled(true);
        settings.setMapToolbarEnabled ( true );
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
            map.addMarker(new MarkerOptions ().position(mylocation).title("Mi posicion"));
            map.animateCamera( CameraUpdateFactory.newLatLngZoom(mylocation,10));
        }

        if(mylocation != null){
            String url = getRequestUrl(mylocation,destino);
            map.addMarker(new MarkerOptions ().position(destino).title("Destino"));
            TaskRequestDirections taskRequestDirections = new TaskRequestDirections ();
            taskRequestDirections.execute ( url );

        }

        // Add a marker in Sydney, Australia, and move the camera.
        // LatLng sv = new LatLng(13.7483454, -89.4907349);
        //map.addMarker(new MarkerOptions().position(sv).title("El Salvador"));
        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(sv,10));
    }

    private String getRequestUrl(LatLng origin, LatLng dest) {
        String str_orig = "origin="+origin.latitude+","+origin.longitude;
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String param = str_orig+"&"+str_dest+"&"+sensor+"&"+mode+"&key="+getString(R.string.map_key);
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+param;
        return url;

    }

    private String requestDirections(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL (reqUrl);
            httpURLConnection = (HttpURLConnection)url.openConnection ();
            httpURLConnection.connect ();

            inputStream = httpURLConnection.getInputStream ();
            InputStreamReader inputStreamReader = new InputStreamReader ( inputStream );
            BufferedReader bufferedReader = new BufferedReader ( inputStreamReader );

            StringBuffer stringBuffer = new StringBuffer (  );
            String line ="";
            while ((line = bufferedReader.readLine ()) != null){
                stringBuffer.append ( line );
            }
            responseString = stringBuffer.toString ();
            bufferedReader.close ();
            inputStreamReader.close ();

        } catch (Exception e){
            e.printStackTrace ();
        } finally {
            if(inputStream != null ){
                inputStream.close ();
            }
            httpURLConnection.disconnect ();

        }
        return responseString;
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String >{

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirections ( strings[0] );
            } catch (IOException e) {
                e.printStackTrace ();
            }
            return  responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute ( s );
            TaskParser taskParser = new TaskParser ();
            taskParser.execute ( s );
        }
    }

    public class TaskParser extends  AsyncTask<String, Void, List<List<HashMap<String, String>>>>{
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject( strings[0] );
                DirectionsParser directionsParser = new DirectionsParser ();
                routes = directionsParser.parse ( jsonObject );
            } catch (JSONException e) {
                e.printStackTrace ();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            super.onPostExecute ( lists );
            ArrayList points = null;
            PolylineOptions polylineOptions = null;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (List<HashMap<String,String>> path :lists){
                points = new ArrayList (  );

                polylineOptions = new PolylineOptions ();
                for(HashMap<String,String> point : path){
                    double lat = Double.parseDouble ( point.get ( "lat" ) );
                    double lon = Double.parseDouble ( point.get ( "lon" ) );
                    LatLng posi =  new LatLng ( lat,lon );
                    points.add (posi );
                    builder.include (  posi);

                }
                polylineOptions.addAll ( points );
                polylineOptions.width ( 10 );
                polylineOptions.color ( Color.BLUE );
                polylineOptions.geodesic ( true );
            }

            if(polylineOptions != null){
                polyline = map.addPolyline ( polylineOptions );
                LatLngBounds bounds = builder.build();
                map.animateCamera(CameraUpdateFactory.newLatLngBounds ( bounds,9 ));
            }else{
                Toast.makeText ( getApplicationContext (),"Directions not Found!",Toast.LENGTH_SHORT ).show ();
            }
        }
    }
}
