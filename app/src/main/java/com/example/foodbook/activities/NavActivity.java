package com.example.foodbook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodbook.R;
import com.example.foodbook.databinding.ActivityNavBinding;
import com.example.foodbook.dialogs.SettingsDialog;
import com.example.foodbook.fragments.HomeFragment;
import com.example.foodbook.fragments.NewPostFragment;
import com.example.foodbook.fragments.ProfileFragment;
import com.example.foodbook.fragments.SearchFragment;
import com.example.foodbook.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavActivity extends AppCompatActivity {
    private ActivityNavBinding binding;
    FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        current_user = FirebaseAuth.getInstance().getCurrentUser();

        FragmentManager fragmentManager = getSupportFragmentManager();

        binding.top10Button.setOnClickListener(view -> {
            Intent intent = new Intent(this, Top10Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        });

        String currentFragmentId = getIntent().getStringExtra("currentFragment");
        if (currentFragmentId != null && !currentFragmentId.equals("HOME")) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (currentFragmentId) {
                case "PROFILE":
                    User user = new User(current_user.getEmail(), current_user.getDisplayName());
                    transaction.add(R.id.fragmentsFrame, ProfileFragment.newInstance(user), "PROFILE");
                    binding.bottomNavigation.setSelectedItemId(R.id.profile);
                    break;
                case "SEARCH":
                    transaction.add(R.id.fragmentsFrame, SearchFragment.newInstance(), "SEARCH");
                    binding.bottomNavigation.setSelectedItemId(R.id.search);
                    break;
                case "NEW_POST":
                    transaction.add(R.id.fragmentsFrame, NewPostFragment.newInstance(), "NEW_POST");
                    binding.bottomNavigation.setSelectedItemId(R.id.new_post);
                    break;
            }
            transaction.commit();
        }

        if (binding.bottomNavigation.getSelectedItemId() == R.id.home) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragmentsFrame, HomeFragment.newInstance(), "HOME");
            transaction.commit();
        }

        binding.settingsButton.setOnClickListener(view -> {
            HomeFragment homeFragment = (HomeFragment)getSupportFragmentManager().findFragmentByTag("HOME");
            ProfileFragment profileFragment = (ProfileFragment)getSupportFragmentManager().findFragmentByTag("PROFILE");
            SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("SEARCH");
            String current_fragment;
            if (homeFragment != null &&  homeFragment.isVisible())
                current_fragment = "HOME";
            else if (profileFragment != null && profileFragment.isVisible())
                current_fragment = "PROFILE";
            else if (searchFragment != null && searchFragment.isVisible())
                current_fragment = "SEARCH";
            else
                current_fragment = "NEW_POST";
            SettingsDialog settingsDialog = new SettingsDialog(current_fragment);
            settingsDialog.show(getSupportFragmentManager(), "");
        });

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                    transaction1.replace(R.id.fragmentsFrame, HomeFragment.newInstance(), "HOME");
                    transaction1.commit();
                    break;
                case R.id.profile:
                    FragmentTransaction transaction3 = fragmentManager.beginTransaction();
                    User user = new User(current_user.getEmail(), current_user.getDisplayName());
                    transaction3.replace(R.id.fragmentsFrame, ProfileFragment.newInstance(user), "PROFILE");
                    transaction3.commit();
                    break;
                case R.id.search:
                    FragmentTransaction transaction4 = fragmentManager.beginTransaction();
                    transaction4.replace(R.id.fragmentsFrame, SearchFragment.newInstance(), "SEARCH");
                    transaction4.commit();
                    break;
                case R.id.new_post:
                    FragmentTransaction transaction2 = fragmentManager.beginTransaction();
                    transaction2.replace(R.id.fragmentsFrame, NewPostFragment.newInstance(), "NEW_POST");
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