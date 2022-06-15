package com.example.foodbook.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbook.R;
import com.example.foodbook.databinding.ActivityHomePageBinding;
import com.example.foodbook.databinding.ActivityMainBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ActivityMainBinding binding;
    private String mail, password;
    private Button signin_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        signin_btn = binding.signinBtn;
        signin_btn.setOnClickListener(view -> {
            mail = binding.etMail.getText().toString();
            password = binding.etPassword.getText().toString();

            if (mail.isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            }
            else if (password.isEmpty()){
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            }
            else {
                firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(this, HomePage.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(this, "email or password are incorrect", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}