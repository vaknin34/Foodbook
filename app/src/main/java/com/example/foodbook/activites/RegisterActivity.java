package com.example.foodbook.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodbook.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

//        firebaseAuth.createUserWithEmailAndPassword().addOnSuccessListener(authResult -> {
//
//        });
    }
}