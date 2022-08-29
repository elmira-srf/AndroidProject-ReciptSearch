package com.example.azadehs_elmiras_project.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.azadehs_elmiras_project.adapter.CardAdapter;
import com.example.azadehs_elmiras_project.databinding.FragmentCardScreenBinding;
import com.example.azadehs_elmiras_project.models.Meal;
import com.example.azadehs_elmiras_project.viewmodels.MealViewModel;

import java.util.ArrayList;
import java.util.List;

public class CardScreenFragment extends Fragment {

    private FragmentCardScreenBinding binding;
    private ArrayList<Meal> mealArrayList = new ArrayList<>();
    private CardAdapter cardAdapter;
    private MealViewModel mealViewModel;
    private int totalCount;
    private TextView display;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        binding = FragmentCardScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardAdapter = new CardAdapter(this.getActivity(),mealArrayList);
        binding.etListview.setAdapter(cardAdapter);

        this.mealViewModel = MealViewModel.getInstance(this.getActivity().getApplication());

        display = binding.tvTotalMeals;

        Log.e("TAG","onViewCreated  **getAllMeals()");
        getAllMeals();

        binding.etListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showAlert(position);
                return false;
            }
        });
    }

    public void getAllMeals(){
        this.mealViewModel.getAllMeals();
        this.mealViewModel.allMeals.observe(this.getActivity(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                if (meals.isEmpty()){
                    Log.e("TAG", "onChanged: No documents received");

                }else{
                    totalCount = 0;
                    for (int i=0; i<meals.size(); i++) {
                        Log.e("TAG", "onChanged: meal count : " + meals.get(i).getCount());
                        totalCount = totalCount + meals.get(i).getCount();
                    }
                    mealArrayList.clear();
                    mealArrayList.addAll(meals);
                    cardAdapter.notifyDataSetChanged();

                    display.setText("Total: "+ String.valueOf(totalCount));
                }
            }
        });
    }

    public void showAlert(int position){
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Delete item");
        alertDialog.setMessage("Are you sure to delete this item?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeMeal(position);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();


    }

    public void removeMeal(int position){

        //delete from database
        mealViewModel.deleteMeal(mealArrayList.get(position).getId());
        mealArrayList.remove(position);

        getAllMeals();
        if (mealArrayList.isEmpty()){
            display.setText("Total: 0");
            mealArrayList.clear();
            cardAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}