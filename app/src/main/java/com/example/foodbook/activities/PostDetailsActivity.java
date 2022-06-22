package com.example.foodbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodbook.R;
import com.example.foodbook.databases.FirebaseStorageManager;
import com.example.foodbook.databinding.ActivityPostDetailsBinding;
import com.example.foodbook.models.Post;
import com.example.foodbook.viewmodels.PostViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PostDetailsActivity extends AppCompatActivity {

    ActivityPostDetailsBinding binding;
    FirebaseUser current_user;
    PostViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        current_user = FirebaseAuth.getInstance().getCurrentUser();
        viewModel = new ViewModelProvider(this).get(PostViewModel.class);

        Post post = (Post) getIntent().getSerializableExtra(getString(R.string.postDetails));

        binding.etDishName.getEditText().setText(post.getDish_name());
        binding.etWriter.getEditText().setText(post.getWriter());
        binding.etDate.getEditText().setText(post.getDate());
        binding.etIngredients.getEditText().setText(post.getIngredients());
        binding.etRecipe.getEditText().setText(post.getRecipe());
        FirebaseStorageManager.downloadImage(post.getMail() + post.getDish_name(), binding.ivDishPhoto);

        if (current_user.getEmail().equals(post.getMail())) {
            binding.deleteBtn.setVisibility(View.VISIBLE);
            binding.editBtn.setVisibility(View.VISIBLE);

            binding.deleteBtn.setOnClickListener(view -> {
                new MaterialAlertDialogBuilder(this)
                        .setMessage("Are you sure you want to delete this post?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            Intent intent = new Intent(this, NavActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            viewModel.delete(post);
                        })
                        .setNegativeButton("No", (dialogInterface, i) -> {})
                        .show();
            });

            binding.editBtn.setOnClickListener(view -> {
                GetEditState();

                binding.saveBtn.setOnClickListener(view1 -> {
                    GetNonEditState();
                    post.setDish_name(binding.etDishName.getEditText().getText().toString());
                    post.setIngredients(binding.etIngredients.getEditText().getText().toString());
                    post.setRecipe(binding.etRecipe.getEditText().getText().toString());
                    viewModel.update(post);
                });

                binding.cnlBtn.setOnClickListener(view1 -> {
                    GetNonEditState();
                    binding.etDishName.getEditText().setText(post.getDish_name());
                    binding.etIngredients.getEditText().setText(post.getIngredients());
                    binding.etRecipe.getEditText().setText(post.getRecipe());
                });
            });
        }
    }

    private void GetEditState() {
        binding.deleteBtn.setVisibility(View.INVISIBLE);
        binding.editBtn.setVisibility(View.INVISIBLE);
        binding.saveBtn.setVisibility(View.VISIBLE);
        binding.cnlBtn.setVisibility(View.VISIBLE);

        binding.etDishName.getEditText().setEnabled(true);
        binding.etIngredients.getEditText().setEnabled(true);
        binding.etRecipe.getEditText().setEnabled(true);
    }

    private void GetNonEditState() {
        binding.deleteBtn.setVisibility(View.VISIBLE);
        binding.editBtn.setVisibility(View.VISIBLE);
        binding.saveBtn.setVisibility(View.INVISIBLE);
        binding.cnlBtn.setVisibility(View.INVISIBLE);

        binding.etDishName.getEditText().setEnabled(false);
        binding.etIngredients.getEditText().setEnabled(false);
        binding.etRecipe.getEditText().setEnabled(false);
    }
}