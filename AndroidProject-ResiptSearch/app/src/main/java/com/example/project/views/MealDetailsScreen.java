package com.example.azadehs_elmiras_project.views;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.azadehs_elmiras_project.R;
import com.example.azadehs_elmiras_project.databinding.ActivityMealDetailsScreenBinding;
import com.example.azadehs_elmiras_project.helper.LocationHelper;
import com.example.azadehs_elmiras_project.models.Favorit;
import com.example.azadehs_elmiras_project.models.Meal;
import com.example.azadehs_elmiras_project.viewmodels.FavoriteViewModel;
import com.example.azadehs_elmiras_project.viewmodels.MealViewModel;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class MealDetailsScreen extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = this.getClass().getCanonicalName();
    private ActivityMealDetailsScreenBinding binding;

    private Meal currentMeal;
    private String CurrentCountry;

    private Meal newMeal;
    private MealViewModel mealViewModel;

    private Favorit newFavorite;
    private FavoriteViewModel favoriteViewModel;

    private int count = 0;

    private LocationHelper locationHelper;
    private Location lastLocation;
    private LocationCallback locationCallback;
    private double Lat;
    private double Lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //configure binding
        binding = ActivityMealDetailsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get data from previous screen
        Intent detailIntent = getIntent();
        if (detailIntent == null) {
            Log.d("TAG", "The intent is null");
        }
        else {
            CurrentCountry = detailIntent.getStringExtra("Current_Country");
            currentMeal = detailIntent.getParcelableExtra("Current_Meal");
            if (this.currentMeal != null){
                this.binding.tvMealNameId.setText(this.currentMeal.getMealName());
                Picasso.get().load(currentMeal.getImageThumb()).into(this.binding.imageMealId);
                Log.e("TAG",currentMeal.getImageThumb().toString());
                //this.binding.descMealId.setText(this.currentMeal.getMealInstructions());
                this.binding.tvCategoryId.setText("Category: " + this.currentMeal.getMealCategory());
                this.binding.tvCountryId.setText("Country: "+ CurrentCountry);
            }
        }//else

        this.newMeal = new Meal();
        this.mealViewModel = MealViewModel.getInstance(this.getApplication());

        this.newFavorite = new Favorit();
        this.favoriteViewModel = FavoriteViewModel.getInstance(this.getApplication());

        this.binding.buttonDecreaseId.setEnabled(false);

        this.locationHelper = LocationHelper.getInstance();
        this.locationHelper.checkPermissionr(this);

        //configure buttons
        this.binding.buttonAddToCartId.setOnClickListener(this);
        this.binding.buttonIncreaseId.setOnClickListener(this);
        this.binding.buttonDecreaseId.setOnClickListener(this);
        this.binding.showLocation.setOnClickListener(this);
        this.binding.favorit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view != null){
            switch (view.getId()){
                case R.id.button_AddToCart_id:{
                    Log.d("TAG", "onClick: Add to Card Button Clicked");

                    this.newMeal.setMealName(this.binding.tvMealNameId.getText().toString());
                    this.newMeal.setMealCategory(this.currentMeal.getMealCategory());
                    this.newMeal.setCount(Integer.parseInt(this.binding.tvCountId.getText().toString()));
                    this.newMeal.setImageThumb(currentMeal.getImageThumb().toString());

//                    Picasso.get().load(this.newMeal.setImageThumb()).into(binding.imageMealId);


                    Log.d("TAG", "onClick: New Meal " + this.newMeal.toString());
                    //save document to DB
                    this.mealViewModel.addMeal(this.newMeal);

                    finish();

                    break;
                }
                case R.id.button_increase_id:{
                    Log.d("TAG", "onClick: increase Button Clicked");
                    count++;
                    this.binding.tvCountId.setText(String.valueOf(count));
                    if (count>0){
                        this.binding.buttonDecreaseId.setEnabled(true);
                    }

                    break;
                }
                case R.id.button_decrease_id:{
                    Log.d("TAG", "onClick: decrease Button Clicked");
                    if (count > 0){
                        count--;
                        this.binding.tvCountId.setText(String.valueOf(count));
                        if (count <= 0){
                            this.binding.buttonDecreaseId.setEnabled(false);
                        }
                    }

                    break;
                }
                case R.id.showLocation:{
                    Log.d("TAG", "onClick: showLocation Button Clicked");

                    this.doReverseGeocoding();
                    this.openMap();

                    break;
                }
                case R.id.favorit:{
                    Log.d("TAG", "onClick: Add to Favorit Button Clicked");

                    this.newFavorite.setFavoriteName(this.binding.tvMealNameId.getText().toString());
                    this.newFavorite.setImageThumb(currentMeal.getImageThumb().toString());
                    this.newFavorite.setRecipt(currentMeal.getMealInstructions());

                    Log.d("TAG", "onClick: New Favorite " + this.newFavorite.toString());
                    //save document to DB
                    this.favoriteViewModel.addFavorite(this.newFavorite);
                    String message = this.binding.tvMealNameId.getText().toString()+ " has been added into favourite";
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.rootLayout),message,Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }//switch
        }//if
    }//onClick

    private void openMap(){
        Log.d(TAG, "onClick: Open map to show location");

        Intent intent = new Intent(this,MapsActivity.class);
        Log.e(TAG,"openMap: Lat : " + Lat + " Lng : " + Lon);

        intent.putExtra("EXRTA_LAT", String.valueOf(Lat));
        intent.putExtra("EXRTA_LNG", String.valueOf(Lon));
        startActivity(intent);
    }
    private void doReverseGeocoding(){
        Log.d(TAG, "onClick: Perform reverse geocoding to get coordinates from address.");

        if (this.locationHelper.locationPermissionGranted){
            LatLng obtainedCoords = this.locationHelper.performReverseGeocoding(this, CurrentCountry);

            if (obtainedCoords != null){
                Log.e(TAG,"Lat : " + obtainedCoords.latitude + "\nLng : " + obtainedCoords.longitude + "Current location " + CurrentCountry);
                Lat = obtainedCoords.latitude;
                Lon = obtainedCoords.longitude;
                this.lastLocation = new Location(obtainedCoords.toString());
            }else{
                Log.d(TAG, "No coordinates obtained");
            }
        }else{
            Log.d(TAG,"Couldn't get coordinates from address");
        }
    }
}