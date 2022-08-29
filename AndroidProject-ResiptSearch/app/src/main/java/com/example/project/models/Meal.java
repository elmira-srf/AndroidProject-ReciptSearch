package com.example.azadehs_elmiras_project.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Meal implements Parcelable {
    private @SerializedName("idMeal") String mealId;
    private @SerializedName("strMeal") String mealName;
    private @SerializedName("strArea") String regionName;
    private @SerializedName("strMealThumb") String imageThumb;
    private @SerializedName("strInstructions") String mealInstructions;
    private @SerializedName("strCategory") String mealCategory;
    private String id;
    private int count = 0;

    //empty constructor
    public Meal(){
    }

    public Meal(String mealName,String regionName, String imageThumb, String mealId , String mealInstructions ,String mealCategory){
        this.mealId = mealId;
        this.mealName = mealName;
        this.regionName = regionName;
        this.imageThumb = imageThumb;
        this.mealInstructions = mealInstructions;
        this.mealCategory = mealCategory;
    }

    public Meal(String mealName, String mealCategory, int count){
        this.mealName = mealName;
        this.mealCategory = mealCategory;
        this.count = count;
    }

    public Meal(String mealName, String mealInstructions, String imageThumb){
        this.mealName = mealName;
        this.mealCategory = mealInstructions;
        this.imageThumb = imageThumb;
    }

    protected Meal(Parcel in) {
        mealId = in.readString();
        mealName = in.readString();
        regionName = in.readString();
        imageThumb = in.readString();
        mealInstructions = in.readString();
        mealCategory = in.readString();
        id = in.readString();
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };


    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }

    public String getMealId() {
        return mealId;
    }

    public String getMealInstructions() {
        return mealInstructions;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(String mealCategory) {
        this.mealCategory = mealCategory;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mealId);
        dest.writeString(mealName);
        dest.writeString(regionName);
        dest.writeString(imageThumb);
        dest.writeString(mealInstructions);
        dest.writeString(mealCategory);
        dest.writeString(id);
    }

    public String getId() {
        return id;
    }
}
