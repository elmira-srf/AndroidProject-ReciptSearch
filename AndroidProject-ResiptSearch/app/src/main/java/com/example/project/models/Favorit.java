package com.example.azadehs_elmiras_project.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorit implements Parcelable {

    private  String favoriteId;
    private  String favoriteName;
    private  String recipt;
    private  String imageThumb;

    public Favorit(){

    }

    public Favorit(String favoriteId,String favoriteName, String recipt, String imageThumb ){
        this.favoriteId = favoriteId;
        this.favoriteName = favoriteName;
        this.recipt = recipt;
        this.imageThumb = imageThumb;
    }


    protected Favorit(Parcel in) {
        favoriteId = in.readString();
        favoriteName = in.readString();
        recipt = in.readString();
        imageThumb = in.readString();
        recipt = in.readString();
    }

    public static final Creator<Favorit> CREATOR = new Creator<Favorit>() {
        @Override
        public Favorit createFromParcel(Parcel in) {
            return new Favorit(in);
        }

        @Override
        public Favorit[] newArray(int size) {
            return new Favorit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(favoriteId);
        parcel.writeString(favoriteName);
        parcel.writeString(recipt);
        parcel.writeString(imageThumb);
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public String getRecipt() {
        return recipt;
    }

    public void setRecipt(String recipt) {
        this.recipt = recipt;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }

    public void setId(String id) {
    }
}
