package com.example.azadehs_elmiras_project.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.azadehs_elmiras_project.models.Meal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealRepository {

    private final FirebaseFirestore DB;

    private final String COLLECTION_MEALS = "meals";
    private final String COLLECTION_USERS = "users";

    private final String FIELD_IMAGE = "imageThumb";
    private final String FIELD_NAME = "mealName";
    private final String FIELD_CATEGORY = "mealCategory";
    private final String FIELD_COUNT = "count";
    public String loggedInUserEmail = "";
    public String loggedInUserPassword = "";

    public MutableLiveData<List<Meal>> allMeals = new MutableLiveData<>();

    public MealRepository() {
        DB = FirebaseFirestore.getInstance();
    }

    public String getUser(){
        return loggedInUserEmail;
    }
    public void addMeal(Meal newMeal){
        try{
            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_NAME, newMeal.getMealName());
            data.put(FIELD_CATEGORY, newMeal.getMealCategory());
            data.put(FIELD_COUNT, newMeal.getCount());
            data.put(FIELD_IMAGE,String.valueOf(newMeal.getImageThumb()));

            //create a new document with randomly generated ID
            DB.collection(COLLECTION_USERS)
                    .document(loggedInUserEmail)
                    .collection(COLLECTION_MEALS)
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.e("TAG", "onSuccess: Document successfully created with ID " + documentReference.getId() );
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "onFailure: Error while creating document" + e.getLocalizedMessage() );
                        }
                    });
        }catch (Exception ex){
            Log.e("TAG", "addFriend: " + ex.getLocalizedMessage() );
        }
    }

    public void getAllMeals(){
        try{
            DB.collection(COLLECTION_USERS)
                    .document(loggedInUserEmail)
                    .collection(COLLECTION_MEALS)
                    .orderBy(FIELD_NAME, Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<Meal> mealList = new ArrayList<>();

                            if (queryDocumentSnapshots.isEmpty()){
                                Log.e("TAG", "onSuccess: No data retrieved");
                            }else{
                                Log.e("TAG", "onSuccess: queryDocumentSnapshots" + queryDocumentSnapshots.getDocumentChanges() );

                                for(DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){
                                    Meal currentMeal = documentChange.getDocument().toObject(Meal.class);
                                    currentMeal.setId(documentChange.getDocument().getId());
                                    mealList.add(currentMeal);
                                    Log.e("TAG", "onSuccess: currentFriend " + currentMeal.toString() );
                                }

                                allMeals.postValue(mealList);
                            }
                        }
                    });
        }catch (Exception ex){
            Log.e("TAG", "getAllFriends: " + ex.getLocalizedMessage() );
        }
    }

    public void deleteMeal(String docID){
        try{
            DB.collection(COLLECTION_USERS)
                    .document(loggedInUserEmail)
                    .collection(COLLECTION_MEALS)
                    .document(docID)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e("TAG", "onSuccess: document successfully deleted");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "onFailure: Unable to delete document" + e.getLocalizedMessage());
                        }
                    });

        }catch (Exception ex){
            Log.e("TAG", "deleteFriend: " + ex.getLocalizedMessage() );
        }
    }//deleteMeal()
}
