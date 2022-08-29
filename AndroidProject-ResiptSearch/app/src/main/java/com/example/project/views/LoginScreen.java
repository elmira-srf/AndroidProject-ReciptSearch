package com.example.azadehs_elmiras_project.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.azadehs_elmiras_project.MainActivity;
import com.example.azadehs_elmiras_project.R;
import com.example.azadehs_elmiras_project.databinding.ActivityLoginScreenBinding;
import com.example.azadehs_elmiras_project.repositories.FavoriteRepository;
import com.example.azadehs_elmiras_project.repositories.MealRepository;
import com.example.azadehs_elmiras_project.viewmodels.FavoriteViewModel;
import com.example.azadehs_elmiras_project.viewmodels.MealViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity  implements View.OnClickListener {

    private ActivityLoginScreenBinding binding;

    private FirebaseAuth mAuth;

    private SharedPreferences prefs;

    private MealViewModel mealViewModel;
    private MealRepository mealRepository;

    private FavoriteViewModel favoriteViewModel;
    private FavoriteRepository favoriteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //configure binding
        this.binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        //configure buttons
        this.binding.buttonRegisterId.setOnClickListener(this);
        this.binding.buttonSigninId.setOnClickListener(this);

        // initialize the object of firebase
        this.mAuth = FirebaseAuth.getInstance();

        prefs = getApplicationContext().getSharedPreferences(getPackageName(), MODE_PRIVATE);
                if (prefs.contains("USER_EMAIL")){
                    //this.binding.etEmailId.setText(this.prefs.getString("USER_EMAIL", ""));
//                    goToMainScreen();
                }
                if (prefs.contains("USER_PASSWORD")){
                    //this.binding.etPasswordId.setText(this.prefs.getString("USER_PASSWORD", ""));
//                    goToMainScreen();
                }


        this.mealViewModel = MealViewModel.getInstance(this.getApplication());
        this.mealRepository = this.mealViewModel.getMealRepository();

        this.favoriteViewModel = FavoriteViewModel.getInstance(this.getApplication());
        this.favoriteRepository = this.favoriteViewModel.getFriendRepository();
    }

    @Override
    public void onClick(View view) {
        if (view != null){
            switch (view.getId()){
                case R.id.buttonSignin_id:{
                    Log.d("TAG", "onClick: Sign In Button Clicked");
                    validateData();
                    break;
                }
                case R.id.buttonRegister_id:{
                    //Go to Register Screen
                    goToRegisterScreen();
                    break;
                }
            }//switch
        }//if
    }//onClick

    private void validateData(){
        Boolean validData = true;
        String email = "";
        String password = "";

        if (this.binding.etEmailId.getText().toString().isEmpty()){
            this.binding.etEmailId.setError("Email Cannot be Empty");
            validData = false;
        }else{
            email = this.binding.etEmailId.getText().toString();
        }

        if (this.binding.etPasswordId.getText().toString().isEmpty()){
            this.binding.etPasswordId.setError("Password Cannot be Empty");
            validData = false;
        }else {
            password = this.binding.etPasswordId.getText().toString();
        }

        if (validData){
            this.signIn(email, password);
        }else{
            Toast.makeText(this, "Please provide correct inputs", Toast.LENGTH_SHORT).show();
        }
    }//validateData

    private void signIn(String email, String password){
        this.mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.e("Email","Email is:" + email);
                    Log.e("Email","password is:" + password);
                    Log.e("TAG","onComplete: Sign in Successful!");

                    //Save email and password in Shared Preferences
                    saveToPrefs(email,password);

                    mealRepository.loggedInUserEmail = email;
                    mealRepository.loggedInUserPassword = password;
                    favoriteRepository.loggedInUserEmail = email;
                    //go to Home screen
                    goToMainScreen();
                } else {
                    Log.e("TAG","onComplete: Sign In Failed!" + task.getException().getLocalizedMessage());
                    Toast.makeText(LoginScreen.this, "Authentication Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void saveToPrefs(String email, String password){

        if (this.binding.switchRememberId.isChecked()) {
            prefs.edit().putString("USER_EMAIL", email).apply();
            prefs.edit().putString("USER_PASSWORD", password).apply();
        }else{
            if (prefs.contains("USER_EMAIL")){
                prefs.edit().remove("USER_EMAIL").apply();
            }
            if (prefs.contains("USER_PASSWORD")){
                prefs.edit().remove("USER_PASSWORD").apply();
            }
        }
    }//saveToPrefs

    private void removeFromPrefs(){
        prefs.edit().remove("USER_PASSWORD").apply();
        prefs.edit().remove("USER_EMAIL").apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intentFromProfileScreen = getIntent();
        if (intentFromProfileScreen == null) {
            Log.d("TAG", "The intent is null");
        }
        else {
            removeFromPrefs();
        }

    }
    private void goToRegisterScreen(){
        this.finishAffinity();
        Intent signUpIntent = new Intent(this, RegisterScreen.class);
        startActivity(signUpIntent);
    }

    private void goToMainScreen(){
        this.finishAffinity();
        Intent MainIntent = new Intent(this, MainActivity.class);
        startActivity(MainIntent);
    }
}
