package com.example.azadehs_elmiras_project.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.azadehs_elmiras_project.models.Favorit;
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

public class FavoriteRepository {
    private final FirebaseFirestore DB;

    private final String COLLECTION_FAVORITES = "favorites";
    private final String COLLECTION_USERS = "users";

    private final String FIELD_ID = "favoriteId";
    private final String FIELD_IMAGE = "imageThumb";
    private final String FIELD_NAME = "favoriteName";
    private final String FIELD_RECIPT = "recipt";
    public String loggedInUserEmail = "";

    public MutableLiveData<List<Favorit>> allFavorites = new MutableLiveData<>();


    public FavoriteRepository() {
        DB = FirebaseFirestore.getInstance();
    }

    public void addFavorite(Favorit newFavorite){
        try{
            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_ID, newFavorite.getFavoriteId());
            data.put(FIELD_NAME, newFavorite.getFavoriteName());
            data.put(FIELD_IMAGE,String.valueOf(newFavorite.getImageThumb()));
            data.put(FIELD_RECIPT , newFavorite.getRecipt());

            //create a new document with randomly generated ID
            DB.collection(COLLECTION_USERS)
                    .document(loggedInUserEmail)
                    .collection(COLLECTION_FAVORITES)
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
            Log.e("TAG", "addFavourite: " + ex.getLocalizedMessage() );
        }
    }

    public void getAllFavorites(){
        try{
            DB.collection(COLLECTION_USERS)
                    .document(loggedInUserEmail)
                    .collection(COLLECTION_FAVORITES)
//                    .whereEqualTo(FIELD_NAME, "John")
                    .orderBy(FIELD_NAME, Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<Favorit> favoritList = new ArrayList<>();

                            if (queryDocumentSnapshots.isEmpty()){
                                Log.e("TAG", "onSuccess: No data retrieved");
                            }else{
                                Log.e("TAG", "onSuccess: queryDocumentSnapshots" + queryDocumentSnapshots.getDocumentChanges() );

                                for(DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){
                                    Favorit currentFav = documentChange.getDocument().toObject(Favorit.class);
                                    currentFav.setId(documentChange.getDocument().getId());
                                    favoritList.add(currentFav);
                                    Log.e("TAG", "onSuccess: currentFriend " + currentFav.toString() );
                                }

                                allFavorites.postValue(favoritList);
                            }
                        }
                    });
        }catch (Exception ex){
            Log.e("TAG", "getAllFriends: " + ex.getLocalizedMessage() );
        }
    }
}
