package com.example.azadehs_elmiras_project.network;

import com.example.azadehs_elmiras_project.models.Country;
import com.example.azadehs_elmiras_project.models.CountryContainer;
import com.example.azadehs_elmiras_project.models.MealContainer;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    String country_URL = "https://restcountries.com/v2/all/";
    String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";


   @GET(country_URL)
   Call<List<Country>> retrieveCountries();

    @GET(country_URL)
    Call<List<CountryContainer>> fetchCountries();

    @GET("./filter.php")
    Call<MealContainer> fetchRecipeByArea(@Query("a") String searchLetter);

    @GET("./lookup.php")
    Call<MealContainer> fetchRecipeById(@Query("i") String id);

}
