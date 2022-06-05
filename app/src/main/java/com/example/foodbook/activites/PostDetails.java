package com.example.foodbook.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodbook.R;
import com.example.foodbook.databinding.ActivityHomePageBinding;
import com.example.foodbook.databinding.ActivityPostDetailsBinding;

public class PostDetails extends AppCompatActivity {

    private ActivityPostDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String dish_name = getIntent().getStringExtra(getString(R.string.dish_name));

        binding.etDishName.setText("Pasta");
        binding.etWriter.setText("Yuval");
        binding.etDate.setText("6.5.22");
        binding.etIngredients.setText("pasta");
        binding.etRecipe.setText("...");
    }
}