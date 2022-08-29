package com.example.azadehs_elmiras_project.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azadehs_elmiras_project.R;
import com.example.azadehs_elmiras_project.adapter.MealAdapter;
import com.example.azadehs_elmiras_project.databinding.FragmentMealScreenBinding;
import com.example.azadehs_elmiras_project.models.Country;
import com.example.azadehs_elmiras_project.models.Meal;
import com.example.azadehs_elmiras_project.models.MealContainer;
import com.example.azadehs_elmiras_project.network.RetrofitClientCountry;
import com.example.azadehs_elmiras_project.network.RetrofitClientMeal;
import com.example.azadehs_elmiras_project.views.MealDetailsScreen;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MealScreenFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private FragmentMealScreenBinding binding;

    private ArrayList<String> regionList;
    private ArrayList<String> countryList;
    private ArrayAdapter<String> countryAdapter;

    private ArrayList<Meal> mealArrayList;
    private MealAdapter mealAdapter;

    private String currentCountry;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //configure binding
        binding = FragmentMealScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.regionList = new ArrayList<>();

        //configure spinner
        this.countryList = new ArrayList<>();
        this.countryAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item,
                this.countryList);
        this.countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerCountry);
        spinner.setAdapter(this.countryAdapter);
        this.getCountryList();
        spinner.setOnItemSelectedListener(this);

        //configure recycler view
        this.mealArrayList = new ArrayList<>();
        this.mealAdapter = new MealAdapter(this.getActivity(),this.mealArrayList,this::onMealItemClicked);
        RecyclerView recyclerView = view.findViewById(R.id.rv_meal_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(this.mealAdapter);

        return view;
    }

    private void onMealItemClicked(Meal meal) {
        Intent detailIntent = new Intent(this.getActivity(), MealDetailsScreen.class);
        detailIntent.putExtra("Current_Meal", meal);
        detailIntent.putExtra("Current_Country",currentCountry);
        startActivity(detailIntent);
    }

    private void getCountryList(){
        Call<List<Country>> countryCall = RetrofitClientCountry.getInstance().getApi().retrieveCountries();
        try {
            countryCall.enqueue(new Callback<List<Country>>() {
                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                    if (response.code() == 200){
                        //extract the data
                        List<Country> mainResponse = response.body();
                        Log.e("TAG", "onResponse: Number of categories : " + mainResponse.size());
                        for (int i=0 ; i < mainResponse.size(); i++){
                        }
                        if (mainResponse.isEmpty()){
                            Log.e("TAG", "onResponse: No country received");
                        }else{
                            countryList.clear();
                            regionList.clear();
                            for (int i=0 ; i < mainResponse.size(); i++){
                                countryList.add(mainResponse.get(i).getCountryName());
                                regionList.add(mainResponse.get(i).getRegionName());
                            }
                            countryAdapter.notifyDataSetChanged();
                        }
                    }else{
                        Log.e("TAG", "onResponse: Unsuccessful response " + response.code() + response.errorBody() );
                    }
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable t) {
                    Log.e("TAG", "onFailure: Failed to get the countryList from API" + t.getLocalizedMessage() );
                }
            });


        }catch (Exception exception){
            Log.e("TAG", "getTestList: Cannot retrieve country list " + exception.getLocalizedMessage() );
        }
    }//getCountryList()

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e("TAG", "onItemClick: Selected country : " + this.countryList.get(position));
        currentCountry = this.countryList.get(position);
        Log.e("TAG", "onItemClick: Selected Region : " + this.regionList.get(position));
        this.fetchRecipeByArea(this.regionList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void fetchRecipeByArea(String countryName){
        binding.ResultId.setText("");
        Call<MealContainer> mealCall = RetrofitClientMeal.getInstance().getApi().fetchRecipeByArea(countryName);
        try{
            mealCall.enqueue(new Callback<MealContainer>() {
                @Override
                public void onResponse(Call<MealContainer> call, Response<MealContainer> response) {
                    //check if the selected region is in our meal list
                    if ((response.body().getRandomRecipe() != null)){
                        if (response.code() == 200) {
                            if (response.body() != null) {
                                Log.d("TAG", "onResponse: " + response.body().getRandomRecipe());

                                ArrayList<Meal> recipes = response.body().getRandomRecipe();
                                Log.d("TAG", "onResponse: Number of regions received : " + recipes.size());

                                for (int i = 0; i < recipes.size(); i++) {
                                    Log.e("TAG", "onResponse: country ID : " + recipes.get(i).getMealId());
                                    fetchRecipeByID(recipes.get(i).getMealId());
                                }
                                mealArrayList.clear();
                            } else {
                                Log.e("TAG", "onResponse: Null response received");
                            }
                        } else {
                            Log.e("TAG", "onResponse: Unsuccessful response received " + response.code());
                        }
                    }else{
                        call.cancel();
                        mealArrayList.clear();
                        //notify the adapter of data change
                        mealAdapter.notifyDataSetChanged();
                        binding.ResultId.setText("There is No " +countryName +" Meal");
                    }
                }

                @Override
                public void onFailure(Call<MealContainer> call, Throwable t) {
                    call.cancel();
                    Log.e("TAG", "onFailure: Failed to get random recipe " + t.getLocalizedMessage() );
                }
            });
        }catch (Exception exception){
            Log.e("TAG", "getRandomRecipe: Cannot retrieve random recipe " + exception.getLocalizedMessage() );
        }
    }

    private void fetchRecipeByID(String Id){
        Call<MealContainer> mealCallID = RetrofitClientMeal.getInstance().getApi().fetchRecipeById(Id);
        try{
            mealCallID.enqueue(new Callback<MealContainer>() {
                @Override
                public void onResponse(Call<MealContainer> call, Response<MealContainer> response) {
                    if (response.code() == 200){
                        if (response.body() != null) {
                            Log.d("TAG", "onResponse: " + response.body().getRandomRecipe());

                            ArrayList<Meal> recipes = response.body().getRandomRecipe();
                            Log.e("TAG", "onResponse: Number of meals received : " + recipes.size());
                            for (int i=0 ; i < recipes.size(); i++){
                                Log.e("TAG", "onResponse: Region Name : " + recipes.get(i).getRegionName());
                            }
                            //mealArrayList.clear();
                            mealArrayList.addAll(recipes);
                            //notify the adapter of data change
                            mealAdapter.notifyDataSetChanged();

                        }else{
                            Log.e("TAG", "onResponse: Null response received");
                        }
                    }else{
                        Log.e("TAG", "onResponse: Unsuccessful response received " + response.code() );
                    }

                    call.cancel();
                }

                @Override
                public void onFailure(Call<MealContainer> call, Throwable t) {
                    call.cancel();
                    Log.e("TAG", "onFailure: Failed to get random recipe " + t.getLocalizedMessage() );
                }
            });
        }catch (Exception exception){
            Log.e("TAG", "getRandomRecipe: Cannot retrieve random recipe " + exception.getLocalizedMessage() );
        }
    }
}