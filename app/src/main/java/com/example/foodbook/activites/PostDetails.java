package com.example.foodbook.activites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbook.R;
import com.example.foodbook.databinding.ActivityPostDetailsBinding;
import com.example.foodbook.models.Post;

public class PostDetails extends AppCompatActivity {

    private ActivityPostDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Post post = (Post) getIntent().getSerializableExtra(getString(R.string.postDetails));

        binding.etDishName.setText(post.getDish_name());
        binding.etWriter.setText(post.getWriter());
        binding.etDate.setText(post.getDate());
        binding.etIngredients.setText(post.getIngredients());
        binding.etRecipe.setText(post.getRecipe());
    }
}