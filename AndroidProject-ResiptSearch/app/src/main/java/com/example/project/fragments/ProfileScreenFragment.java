package com.example.azadehs_elmiras_project.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.azadehs_elmiras_project.R;
import com.example.azadehs_elmiras_project.databinding.FragmentProfileScreenBinding;
import com.example.azadehs_elmiras_project.repositories.MealRepository;
import com.example.azadehs_elmiras_project.viewmodels.MealViewModel;
import com.example.azadehs_elmiras_project.views.LoginScreen;


public class ProfileScreenFragment extends Fragment implements View.OnClickListener{

    private FragmentProfileScreenBinding binding;

    private MealViewModel mealViewModel;
    private MealRepository mealRepository;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.mealViewModel = MealViewModel.getInstance(this.getActivity().getApplication());
        this.mealRepository = this.mealViewModel.getMealRepository();

        binding.tvEmail.setText("User Email: " + mealRepository.loggedInUserEmail);
        binding.tvPassword.setText("User Password: " +mealRepository.loggedInUserPassword);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogout.setOnClickListener(this);

        this.mealViewModel = MealViewModel.getInstance(this.getActivity().getApplication());
        this.mealRepository = this.mealViewModel.getMealRepository();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if (view != null){
            switch (view.getId()){
                case R.id.button_logout:{
                    //Go to Login Screen
                    goToLoginScreen();
                    break;
                }
            }//switch
        }//if
    }//onClick

    private void goToLoginScreen(){
        this.getActivity().finishAffinity();
        Intent LoginIntent = new Intent(this.getActivity(), LoginScreen.class);
        LoginIntent.putExtra("User_Logout", "true");
        startActivity(LoginIntent);
    }


}