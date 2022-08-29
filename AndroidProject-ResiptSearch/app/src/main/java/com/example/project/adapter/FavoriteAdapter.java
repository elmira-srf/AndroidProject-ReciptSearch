package com.example.azadehs_elmiras_project.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azadehs_elmiras_project.clickListener.OnFavoriteClickListener;
import com.example.azadehs_elmiras_project.databinding.FavoriteItemsListBinding;
import com.example.azadehs_elmiras_project.models.Favorit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private ArrayList<Favorit> favoriteArrayList;
    private Context context;
    private OnFavoriteClickListener clickListener;
    FavoriteItemsListBinding binding;

    public FavoriteAdapter(Context context, ArrayList<Favorit> favorites){
        this.favoriteArrayList = favorites;
        this.context = context;
        this.clickListener = clickListener;
    }

    public FavoriteAdapter(Context context, ArrayList<Favorit> favorits, OnFavoriteClickListener clickListener){
        this.favoriteArrayList = favorits;
        this.context = context;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(FavoriteItemsListBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Favorit currentFavourite = favoriteArrayList.get(position);
        holder.bind(context, currentFavourite, clickListener);
    }

    @Override
    public int getItemCount() {
        Log.d("RecipeAdapter", "getItemCount: Number of recipes " +this.favoriteArrayList.size() );
        return this.favoriteArrayList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder{
        FavoriteItemsListBinding itemBinding;

        public FavoriteViewHolder(FavoriteItemsListBinding binding){
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        public void bind(Context context, Favorit fav, OnFavoriteClickListener clickListener){
            itemBinding.tvMealName.setText(fav.getFavoriteName());
            itemBinding.tvRegion.setText(fav.getRecipt());
            Picasso.get().load(fav.getImageThumb()).into(itemBinding.imgMeal);
        }
    }
}
