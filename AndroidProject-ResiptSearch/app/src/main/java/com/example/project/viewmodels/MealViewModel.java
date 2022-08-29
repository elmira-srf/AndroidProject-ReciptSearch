package com.example.azadehs_elmiras_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.azadehs_elmiras_project.models.Meal;
import com.example.azadehs_elmiras_project.repositories.MealRepository;

import java.util.List;

public class MealViewModel extends AndroidViewModel {

    private final MealRepository repository = new MealRepository();
    private static MealViewModel instance;
    public MutableLiveData<List<Meal>> allMeals;

    public MealViewModel(@NonNull Application application) {
        super(application);
    }

    public static MealViewModel getInstance(Application application){
        if (instance == null){
            instance = new MealViewModel(application);
        }
        return instance;
    }

    public MealRepository getMealRepository(){
        return this.repository;
    }

    public void addMeal(Meal meal){
        this.repository.addMeal(meal);
    }

    public void getAllMeals(){
        this.repository.getAllMeals();
        this.allMeals = this.repository.allMeals;
    }

    public void deleteMeal(String docID){ this.repository.deleteMeal(docID);}
}
