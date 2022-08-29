package com.example.azadehs_elmiras_project.models;

import com.google.gson.annotations.SerializedName;

public class Country {
    private @SerializedName("name") String countryName;
    private @SerializedName("alpha3Code") String countryCode;
    private @SerializedName("demonym") String regionName;


    public String getCountryName() {
        return countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                '}';
    }
}
