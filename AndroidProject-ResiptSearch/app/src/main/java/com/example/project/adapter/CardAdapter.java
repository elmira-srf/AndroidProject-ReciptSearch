package com.example.azadehs_elmiras_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.azadehs_elmiras_project.R;
import com.example.azadehs_elmiras_project.databinding.CustomRowLayoutBinding;
import com.example.azadehs_elmiras_project.models.Meal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends ArrayAdapter<Meal> {


    public CardAdapter(@NonNull Context context, @NonNull List<Meal> objects) {
        super(context, 0,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        CustomRowLayoutBinding binding;
        if( convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_row_layout,parent,false);
        }
        binding = CustomRowLayoutBinding.bind(convertView);
        Meal currMeal = getItem(position);
        binding.maintitleview.setText(currMeal.getMealName());
        binding.subtitleview.setText(String.valueOf(currMeal.getMealCategory()));
        binding.tvCount.setText("Quantity:" + String.valueOf(currMeal.getCount()));
        Picasso.get().load(currMeal.getImageThumb()).into(binding.image);
        return convertView;

    }

}
