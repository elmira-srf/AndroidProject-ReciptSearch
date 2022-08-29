package com.example.azadehs_elmiras_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.azadehs_elmiras_project.R;
import com.example.azadehs_elmiras_project.databinding.ActivityMapsBinding;
import com.example.azadehs_elmiras_project.helper.LocationHelper;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final String TAG = this.getClass().getCanonicalName();
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LocationCallback locationCallback;
    private LocationHelper locationHelper;
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        if (intent == null) {
            Log.d("TAG", "The intent is null");
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String Lat = intent.getStringExtra("EXRTA_LAT");
        String Lon = intent.getStringExtra("EXRTA_LNG");

        Log.e(TAG, "double value of lat: " + Double.valueOf(Lat) + " double value of lon: " + Double.valueOf(Lon));
        if(this.getIntent() != null){
            this.currentLocation = new LatLng(this.getIntent().getDoubleExtra("EXTRA_LAT", Double.valueOf(Lat)),
                    this.getIntent().getDoubleExtra("EXTRA_LNG", Double.valueOf(Lon)));
            Log.e(TAG, "onCreate: currentLocation: Lat: " + currentLocation.latitude + " Lng: " + currentLocation.longitude);
        }

        this.locationHelper = LocationHelper.getInstance();

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(this.currentLocation).title("This is your current location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.currentLocation, 5.0f));

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setTrafficEnabled(true);

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
    }
}