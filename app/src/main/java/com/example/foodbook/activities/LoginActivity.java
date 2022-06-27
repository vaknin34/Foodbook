package com.example.foodbook.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.foodbook.databinding.ActivityLoginBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ActivityLoginBinding binding;
    private String mail, password;

    SharedPreferences loginPreference;
    String MY_PREF = "my_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        loginPreference = getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);

        if(loginPreference.getString("firstTime", "notok").equals("notok")){

            SharedPreferences.Editor edit = loginPreference.edit();
            edit.putString("firstTime", "no");
            edit.commit();

            new MaterialAlertDialogBuilder(this)
                    .setTitle("Welcome To FoodBook!")
                    .setMessage("This is where you can search for recipes and share your own")
                    .setPositiveButton("Continue", (dialogInterface, i) -> {})
                    .show();
        }

        firebaseAuth = FirebaseAuth.getInstance();

        binding.gotoRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.signinBtn.setOnClickListener(view -> {
            mail = binding.etMail.getEditText().getText().toString();
            password = binding.etPassword.getEditText().getText().toString();

            if (mail.isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            }
            else if (password.isEmpty()){
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            }
            else {
                firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        firebaseAuth.addAuthStateListener(firebaseAuth -> {
                            Intent intent = new Intent(this, NavActivity.class);
                            startActivity(intent);
                        });
                    }
                    else {
                        Toast.makeText(this, "email or password are incorrect", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}