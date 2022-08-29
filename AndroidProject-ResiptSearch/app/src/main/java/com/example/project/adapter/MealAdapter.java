package com.example.azadehs_elmiras_project.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azadehs_elmiras_project.clickListener.OnMealClickListener;
import com.example.azadehs_elmiras_project.databinding.MealsItemListBinding;
import com.example.azadehs_elmiras_project.models.Country;
import com.example.azadehs_elmiras_project.models.Meal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder>{
    private ArrayList<Meal> mealArrayList;
    private ArrayList<Country> countryArrayList;
    private Context context;
    private OnMealClickListener clickListener;
    MealsItemListBinding binding;

    public MealAdapter(Context context,ArrayList<Meal> meals, OnMealClickListener clickListener){
        this.mealArrayList = meals;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealViewHolder(MealsItemListBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal currentMeal = mealArrayList.get(position);
        holder.bind(context, currentMeal, clickListener);
    }

    @Override
    public int getItemCount() {
        Log.d("RecipeAdapter", "getItemCount: Number of recipes " +this.mealArrayList.size() );
        return this.mealArrayList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder{
        MealsItemListBinding itemBinding;

        public MealViewHolder(MealsItemListBinding binding){
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        public void bind(Context context, Meal currentMeal,OnMealClickListener clickListener){
            itemBinding.tvMealName.setText(currentMeal.getMealName());
            itemBinding.tvRegion.setText(currentMeal.getRegionName());
            Picasso.get().load(currentMeal.getImageThumb()).into(itemBinding.imgMeal);

            itemBinding.imgMeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onMealItemClicked(currentMeal);
                }
            });
        }
    }

}
