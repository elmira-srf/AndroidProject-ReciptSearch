package com.example.azadehs_elmiras_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.azadehs_elmiras_project.models.Favorit;
import com.example.azadehs_elmiras_project.repositories.FavoriteRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private final FavoriteRepository repository = new FavoriteRepository();
    private static FavoriteViewModel instance;
    public MutableLiveData<List<Favorit>> allFavorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
    }

    public static FavoriteViewModel getInstance(Application application){
        if (instance == null){
            instance = new FavoriteViewModel(application);
        }
        return instance;
    }

    public FavoriteRepository getFriendRepository(){
        return this.repository;
    }

    public void addFavorite(Favorit fav){
        this.repository.addFavorite(fav);
    }

    public void getAllFavorites(){
        this.repository.getAllFavorites();
        this.allFavorites = this.repository.allFavorites;
    }
}


