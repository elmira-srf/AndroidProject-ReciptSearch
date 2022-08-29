package com.example.azadehs_elmiras_project.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FavoritContainer {
        private @SerializedName("favorits")
        ArrayList<Favorit> favoritMeal;

        public ArrayList<Favorit> getFavoritMeal() {
                return favoritMeal;
        }

        @Override
        public String toString() {
                return "FavoritContainer{" +
                        "favoritMeal=" + favoritMeal +
                        '}';
        }
}
