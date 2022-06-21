package com.example.foodbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.foodbook.R;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.databinding.ActivityNavBinding;
import com.example.foodbook.databinding.ActivityPostDetailsBinding;
import com.example.foodbook.models.Post;

public class PostDetailsActivity extends AppCompatActivity {

    ActivityPostDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Post post = (Post) getIntent().getSerializableExtra(getString(R.string.postDetails));

        binding.expandableText.setText("dskfjkjdklfjkldfjkdlfjdklfjdlfjdf;lj");

        binding.etDishName.setText(post.getDish_name());
        binding.etWriter.setText(post.getWriter());
        binding.etDate.setText(post.getDate());
//        binding.etIngredients.setText(post.getIngredients());
        binding.etRecipe.setText(post.getRecipe());
        FirebaseStorageManager.downloadImage(post.getMail() + post.getDish_name(), binding.ivDishPhoto);
    }
}