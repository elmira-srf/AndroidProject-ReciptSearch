package com.example.azadehs_elmiras_project.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientCountry {
    private static RetrofitClientCountry instance = null;
    private API api;

    private RetrofitClientCountry(){
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api.country_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(API.class);
    }

    public API getApi(){
        return api;
    }

    public static synchronized RetrofitClientCountry getInstance(){
        if ( instance == null){
            instance = new RetrofitClientCountry();
        }
        return instance;
    }
}
