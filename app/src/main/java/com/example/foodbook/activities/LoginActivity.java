package com.example.foodbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbook.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ActivityLoginBinding binding;
    private String mail, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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