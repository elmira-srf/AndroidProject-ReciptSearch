package com.example.azadehs_elmiras_project.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MealContainer {
    private @SerializedName("meals") ArrayList<Meal> randomRecipe;

    public ArrayList<Meal> getRandomRecipe() {
        return randomRecipe;
    }

    @Override
    public String toString() {
        return "RecipeContainer{" +
                "randomRecipe=" + randomRecipe +
                '}';
    }
}
