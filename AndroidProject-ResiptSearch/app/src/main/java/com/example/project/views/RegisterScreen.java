package com.example.azadehs_elmiras_project.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.azadehs_elmiras_project.R;
import com.example.azadehs_elmiras_project.databinding.ActivityRegisterScreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener{

    private ActivityRegisterScreenBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //configure binding
        this.binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        //configure sign up button
        this.binding.buttonSignUpId.setOnClickListener(this);

        // initialize the object of firebase
        this.mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if (view != null){
            switch (view.getId()){
                case R.id.buttonSignUp_id:{
                    Log.d("TAG", "onClick: Sign up button Clicked");
                    validateData();
                    break;
                }
            }//switch
        }//if
    }//onClick()

    private void validateData(){
        Boolean validData = true;
        String name = "";
        String email = "";
        String password = "";
        Integer age = 0;

        if (this.binding.etNameId.getText().toString().isEmpty()){
            this.binding.etNameId.setError("Name Cannot be Empty");
            validData = false;
        } else {
            name = this.binding.etNameId.getText().toString();
        }

        if (this.binding.etAgeId.getText().toString().isEmpty()){
            this.binding.etAgeId.setError("Name Cannot be Empty");
            validData = false;
        } else {
            age = Integer.parseInt(this.binding.etAgeId.getText().toString());
        }

        if (this.binding.etEmailRegisterId.getText().toString().isEmpty()){
            this.binding.etEmailRegisterId.setError("Email Cannot be Empty");
            validData = false;
        }else{
            email = this.binding.etEmailRegisterId.getText().toString();
        }

        if (this.binding.etPasswordRegisterId.getText().toString().isEmpty()){
            this.binding.etPasswordRegisterId.setError("Password Cannot be Empty");
            validData = false;
        }else {
            if (this.binding.etPasswordConfirmId.getText().toString().isEmpty()) {
                this.binding.etPasswordConfirmId.setError("Confirm Password Cannot be Empty");
                validData = false;
            } else {
                if (!this.binding.etPasswordRegisterId.getText().toString().equals(this.binding.etPasswordConfirmId.getText().toString())) {
                    this.binding.etPasswordConfirmId.setError("Both passwords must be same");
                    validData = false;
                }else{
                    password = this.binding.etPasswordConfirmId.getText().toString();
                }
            }
        }

        if (validData){
            this.createAccount(email, password);
        }else{
            Toast.makeText(this, "Please provide correct inputs", Toast.LENGTH_SHORT).show();
        }
    } //validateData()

    private void createAccount(String email, String password){
        this.mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //*****
                    //Save email and password in Shared Preferences
                    //saveToPrefs(email,password);

                    Toast.makeText(RegisterScreen.this, "Account has been created",Toast.LENGTH_SHORT).show();
                    //go to login screen
                    goToMain();
                } else {
                    Log.e("TAG","onComplete: Register Failed!");
                    Toast.makeText(RegisterScreen.this, "Register Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }//createAccount()

    private void goToMain(){
        this.finishAffinity();
        Intent mainIntent = new Intent(this, LoginScreen.class);
        startActivity(mainIntent);
    }
}