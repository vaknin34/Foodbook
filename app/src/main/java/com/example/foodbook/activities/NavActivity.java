package com.example.foodbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import com.example.foodbook.R;
import com.example.foodbook.databinding.ActivityNavBinding;
import com.example.foodbook.fragments.HomeFragment;
import com.example.foodbook.fragments.NewPostFragment;
import com.example.foodbook.fragments.ProfileFragment;

public class NavActivity extends AppCompatActivity {
    private ActivityNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                    FragmentTransaction transaction3 = fragmentManager.beginTransaction();
                    transaction3.addToBackStack(null);
                    transaction3.add(R.id.fragmentsFrame, ProfileFragment.newInstance(), "whatever");
                    transaction3.commit();
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