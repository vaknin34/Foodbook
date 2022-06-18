package com.example.foodbook.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.foodbook.R;
import com.example.foodbook.databinding.ActivityHomePageBinding;
import com.example.foodbook.databinding.ActivityNavBinding;
import com.example.foodbook.fragments.HomeFragment;
import com.example.foodbook.fragments.NewPostFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavActivity extends AppCompatActivity {
    Context context;
    private ActivityNavBinding binding;
    private FirebaseUser current_user;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;

        firebaseAuth = FirebaseAuth.getInstance();
        current_user =(FirebaseUser)getIntent().getExtras().get("user");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragmentsFrame, HomeFragment.newInstance(), "whatever");
        transaction.commit();

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                    transaction1.addToBackStack(null);
                    transaction1.add(R.id.fragmentsFrame, HomeFragment.newInstance(), "whatever");
                    transaction1.commit();
                    break;
                case R.id.profile:
                    break;
                case R.id.new_post:
                    FragmentTransaction transaction2 = fragmentManager.beginTransaction();
                    transaction2.addToBackStack(null);
                    transaction2.add(R.id.fragmentsFrame, NewPostFragment.newInstance(), "whatever");
                    transaction2.commit();
                    break;
                case  R.id.log_out:
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
            }
            return true;
        });
    }
}