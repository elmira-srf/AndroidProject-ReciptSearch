package com.example.azadehs_elmiras_project.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.azadehs_elmiras_project.views.MealDetailsScreen;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public class LocationHelper {

    private final String TAG = this.getClass().getCanonicalName();
    public boolean locationPermissionGranted = false;
    private final  int REQUEST_CODE_LOCATION = 101;

    private static final LocationHelper instance = new LocationHelper();
    private FusedLocationProviderClient fusedLocationProviderClient;
    //    private Location myLocation;
    MutableLiveData<Location> myLocation = new MutableLiveData<>();
    private LocationResult locationRequest;


    public static LocationHelper getInstance(){
        return instance;
    }
    public void checkPermissionr(Context context){
        this.locationPermissionGranted =
                (PackageManager.PERMISSION_GRANTED == (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)));
        Log.e(TAG, "checkPermissions: LocationPermissionGranted: " + this.locationPermissionGranted);

        if(!this.locationPermissionGranted){
            //request permission
            requestLocationPermission(context);
        }
    }
    public void requestLocationPermission(Context context){
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
    }

    public Address perfomeForwardGeocoding(Context context, Location loc){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList;

        try{
            addressList = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            if(addressList.size() > 0){
                Address addressObj = addressList.get(0);

                Log.e(TAG, "performForwardGeocoding: Address " + addressObj.getAddressLine(0));
                Log.e(TAG, "performForwardGeocoding: Postal Code " + addressObj.getPostalCode());
                Log.e(TAG, "performForwardGeocoding: Country Code " + addressObj.getCountryCode());
                Log.e(TAG, "performForwardGeocoding: Thoroughfare " + addressObj.getThoroughfare());
                Log.e(TAG, "performForwardGeocoding:  " + addressObj.getThoroughfare());

                return addressObj;
            }
        }catch (Exception ex){
            Log.e(TAG, "performForwardGeocoding: Couldn't get the addres for the given location coordinator" + ex.getLocalizedMessage() );
        }
        return null;
    }

    public LatLng performReverseGeocoding(MealDetailsScreen mealDetailsScreen, String currentCountry) {
        Geocoder geocoder = new Geocoder(mealDetailsScreen, Locale.getDefault());
        try{
            List<Address> locationList = geocoder.getFromLocationName(currentCountry, 1);

            if(locationList.size() > 0){
                LatLng obtainedCoords = new LatLng(locationList.get(0).getLatitude(), locationList.get(0).getLongitude());

                Log.e(TAG, "perfomforReverseGeocoding: Obtained Coords: " + obtainedCoords.toString());
                return  obtainedCoords;
            }
        }catch (Exception ex){
            Log.e(TAG, "can't get the lat " + ex.getLocalizedMessage());
        }
        return null;
    }
}
