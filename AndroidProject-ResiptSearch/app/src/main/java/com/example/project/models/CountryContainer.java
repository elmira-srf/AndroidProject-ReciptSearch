package com.example.azadehs_elmiras_project.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryContainer {
    private @SerializedName("categories") ArrayList<Country> CountryList;

    public ArrayList<Country> getCountryList() {
        return CountryList;
    }

    @Override
    public String toString() {
        return "CategoryContainer{" +
                "categoryList=" + CountryList +
                '}';
    }
}
